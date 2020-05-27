package com.android.kors.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.kors.R;
import com.android.kors.helperClasses.Communicator;
import com.android.kors.helperClasses.DbHelper;
import com.android.kors.helperClasses.ListViewItemCheckboxBaseAdapter;
import com.android.kors.helperClasses.ListViewItemDataObject;
import com.android.kors.helperClasses.SetWallpaper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTaskActivity extends AppCompatActivity {
    private Communicator communicator = new Communicator();
    private SetWallpaper sw = new SetWallpaper();
    private static final String TAG1 = "DeletionProcess";
    private static final String TAG2 = "AdditionProcess";
    private static final String TAG3 = "EnteringListener";
    private static final String DEBUG = "Debug Value";
    ViewFlipper viewFlipper;
    LayoutInflater inflater;
    View newRowView;
    View nextRowView;
    List<EditText> taskListEditText;
    ArrayList<String> taskNameStrListFromServer = new ArrayList<>();
    TableLayout tableContainer;
    List<ListViewItemDataObject> taskItemDataObjectList;
    ListViewItemCheckboxBaseAdapter taskAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.edit_task_listview);
        viewFlipper = findViewById(R.id.taskTabViewFlipper);

        DbHelper db = new DbHelper(getApplicationContext());
        final int wallId = db.getCurrentWallpaperPreference();
        ViewGroup wallView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        sw.MyWall(wallId, wallView);

        final int GroupId = db.getCurrentGroupId();
        final ListView taskListView = findViewById(R.id.taskListViewTab);

        communicator.getTaskNameListByGroupId(GroupId, new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call,@NonNull Response<List<String>> response) {
                if(response.isSuccessful() && response.body() != null){
                    taskNameStrListFromServer.addAll(response.body());

                    getInitViewItemDataObjectList(taskNameStrListFromServer); //convert to dataObject
                    taskAdapter = new ListViewItemCheckboxBaseAdapter(EditTaskActivity.this, taskItemDataObjectList);
                    taskAdapter.notifyDataSetChanged();
                    taskListView.setAdapter(taskAdapter);

                    taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // get user selected item
                            Object itemObject = parent.getAdapter().getItem(position);
                            // translate the selected item to data object
                            ListViewItemDataObject itemDataObject = (ListViewItemDataObject) itemObject;
                            //get the checkbox
                            CheckBox itemCheckbox = view.findViewById(R.id.checkBoxTabItem);

                            // reverse the checkbox and clicked item check state
                            if (itemDataObject.isChecked()) {
                                Log.i(TAG3, "isChecked is working");
                                itemCheckbox.setChecked(false);
                                itemDataObject.setChecked(false);
                                taskItemDataObjectList.get(position).setChecked(false);
                            } else {
                                Log.i(TAG3, "isNotChecked is working");
                                itemCheckbox.setChecked(true);
                                itemDataObject.setChecked(true);
                                taskItemDataObjectList.get(position).setChecked(true);
                            }
                        }
                    });
                } else {
                    Log.e("Error Code", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<String>> call,@NonNull Throwable t) {
                Log.d(DEBUG, String.valueOf(t));
            }
        });

        Button deleteTaskBtn = findViewById(R.id.tabTaskDeleteButton);
        deleteTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Alert dialog should detect if isChecked count is > 0
                int size = taskItemDataObjectList.size();
                List<String> namesToDelete = new ArrayList<>();
                int isCheckedCount = 0;

                for(int i = 0; i < size; i++){

                    ListViewItemDataObject taskNameCheckBox = taskItemDataObjectList.get(i);
                    if(taskNameCheckBox.isChecked()){
                        isCheckedCount++;
                        String name = taskNameCheckBox.getItemText();
                        namesToDelete.add(name);

                        if(name.equals(taskNameStrListFromServer.get(i))){
                            taskNameStrListFromServer.remove(i);
                        }

                        taskItemDataObjectList.remove(i);
                        i--;
                        size = taskItemDataObjectList.size();
                    }
                }

                if(isCheckedCount == 0){
                    Toast.makeText(getApplicationContext(),
                            "No task is selected", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(EditTaskActivity.this).create();
                    alertDialog.setTitle("Warning Message");
                    alertDialog.setMessage("Are you sure to remove selected items?");
                    alertDialog.setIcon(R.mipmap.warning);

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //update name list for display
                            updateTaskList(taskNameStrListFromServer);

                            // get the communicator to delete tasks in db
                            communicator.deleteTaskName(GroupId, namesToDelete, new Callback<Void>() {
                                @Override
                                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                    if(response.isSuccessful()){
                                        Log.i(TAG1, "Deletion process successful");
                                    } else {
                                        Log.e("Error Code", String.valueOf(response.code()));
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                    Log.d(DEBUG, String.valueOf(t));
                                }
                            });
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        Button addTaskBtn = findViewById(R.id.tabTaskAddButton);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewFlipper.setDisplayedChild(1);
            }
        });

        // adding room content view
        taskListEditText = new ArrayList<EditText>();
        tableContainer = findViewById(R.id.tasksTabTableContainer);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        newRowView = inflater.inflate(R.layout.new_edit_text, tableContainer, false);
        tableContainer.addView(newRowView);

        final EditText taskItemDataObjectList = newRowView.findViewById(R.id.newEditText);
        taskItemDataObjectList.requestFocus();

        Button addNewTaskBtn = findViewById(R.id.addTaskButtonOnTaskTab);
        addNewTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the idx[0] to the list
                taskListEditText.add(taskItemDataObjectList); // idx[0]
                if(taskListEditText.size() < 1){ // list is empty
                    // check initial box, if empty warn user
                    if(taskItemDataObjectList.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a room name before adding another box", Toast.LENGTH_SHORT).show();
                    }
                    else { // if  not empty -- allow new box to user -- add new box to container.
                        tableContainer.addView(nextRowView);
                        EditText nextTask = nextRowView.findViewById(R.id.newEditText);
                        nextTask.requestFocus();
                        taskListEditText.add(nextTask);
                    }
                }
                else { // check if next boxes is empty
                    if(taskListEditText.get(taskListEditText.size() - 1).getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a room name before adding another box", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        taskListEditText.add(taskListEditText.get(taskListEditText.size() - 1));

                        // allow new box to user -- add new box to container.
                        tableContainer.addView(nextRowView);
                        EditText nextTask = nextRowView.findViewById(R.id.newEditText);
                        nextTask.requestFocus();
                        taskListEditText.add(nextTask);
                    }
                }
            }
        });

        ImageButton homeBtn = findViewById(R.id.taskRoomToAdminRoom);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditTaskActivity.this, AdminRoomActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        Button saveNewTasksBtn = findViewById(R.id.saveNewTaskButtonOnTaskTab);
        saveNewTasksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> newTaskNamesToSave = new ArrayList<>();
                taskListEditText.add(taskItemDataObjectList); // idx[0]
                if(taskListEditText.size() == 0){
                    Toast.makeText(getApplicationContext(),
                            "You have no task name to save", Toast.LENGTH_SHORT).show();
                }
                else if (taskListEditText.size() == 1){
                    // check the last box -- warn user if empty -- case only 1 box and is empty
                    if(taskListEditText.get(0).getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),
                                "Please enter a task name to save your new task list", Toast.LENGTH_SHORT).show();
                    }
                    else { // case: only 1 box and is NOT empty
                        for(int i = 0; i < taskListEditText.size(); i++){
                            String taskName = taskListEditText.get(i).getText().toString();
                            newTaskNamesToSave.add(taskName);
                        }


                        //clear boxes except 1
                        if(taskListEditText.size() > 1){
                            for(int i = 0; i < taskListEditText.size() - 1; i++){
                                tableContainer.removeView(taskListEditText.get(i));
                                taskListEditText.get(i).setText("");
                            }
                        }

                        // updated list
                        taskNameStrListFromServer.addAll(newTaskNamesToSave);
                        updateTaskList(taskNameStrListFromServer);

                        communicator.postAddTaskName(newTaskNamesToSave, GroupId, new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if(response.isSuccessful()){
                                    Log.i(TAG2, "Room addition process successful");
                                } else {
                                    Log.e("Error Code", String.valueOf(response.code()));
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Log.d(DEBUG, String.valueOf(t));
                            }
                        });

                        Toast.makeText(getApplicationContext(),
                                "New task list saved", Toast.LENGTH_SHORT).show();
                        viewFlipper.setDisplayedChild(0);
                    }
                }
                else {
                    // case: more than 1 box -- must go through non-empty validation
                    int emptyBoxCount = taskListEditText.size();

                    for(int i = 0; i < taskListEditText.size(); i++){
                        if(taskListEditText.get(i).getText().toString().isEmpty()){
                            // empty box count stays
                        } else {
                            emptyBoxCount--;
                        }
                    }

                    if(emptyBoxCount != 0){
                        Toast.makeText(getApplicationContext(),
                                "There are empty box(es) in your list. Please fill in the box(es) to continue.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for(int i = 0; i < taskListEditText.size(); i++){
                            String taskName = taskListEditText.get(i).getText().toString();
                            newTaskNamesToSave.add(taskName);
                        }

                        //clear boxes except 1
                        if(taskListEditText.size() > 1){
                            for(int i = 0; i < taskListEditText.size() - 1; i++){
                                tableContainer.removeView(taskListEditText.get(i));
                            }
                        }

                        // updated list
                        taskNameStrListFromServer.addAll(newTaskNamesToSave);
                        updateTaskList(taskNameStrListFromServer);

                        communicator.postAddTaskName(newTaskNamesToSave, GroupId, new Callback<Void>() {
                            @Override
                            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                                if(response.isSuccessful()){
                                    Log.i(TAG2, "Task addition process successful");
                                } else {
                                    Log.e("Error Code", String.valueOf(response.code()));
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                                Log.d(DEBUG, String.valueOf(t));
                            }
                        });
                        viewFlipper.setDisplayedChild(0);
                    }
                }
            }
        });

        // cancel adding new tasks BUTTON here
        Button cancelAddingTaskBtn = findViewById(R.id.cancelTaskButtonOnTaskTab);
        cancelAddingTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear boxes except 1
                if(taskListEditText.size() > 1){
                    for(int i = 0; i < taskListEditText.size() - 1; i++){
                        tableContainer.removeView(taskListEditText.get(i));
                    }
                }

                viewFlipper.setDisplayedChild(0);
            }
        });
    }

    private void updateTaskList(ArrayList<String> newList){
        taskItemDataObjectList.clear();
        int length = newList.size();

        for(int i = 0; i < length; i++){
            ListViewItemDataObject singleRowObject = new ListViewItemDataObject();

            singleRowObject.setChecked(false);
            String taskName = newList.get(i);
            singleRowObject.setItemText(taskName);

            taskItemDataObjectList.add(singleRowObject);
        }
        taskAdapter.notifyDataSetChanged();
    }

    private void getInitViewItemDataObjectList(ArrayList<String> inParam_taskNameList){
        taskItemDataObjectList = new ArrayList<ListViewItemDataObject>();
        int length = inParam_taskNameList.size();

        for(int i = 0; i < length; i++){
            ListViewItemDataObject singleRowObject = new ListViewItemDataObject();

            singleRowObject.setChecked(false);
            String taskName = inParam_taskNameList.get(i);
            singleRowObject.setItemText(taskName);

            taskItemDataObjectList.add(singleRowObject);
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        //clear boxes except 1
        if(taskListEditText.size() > 1){
            for(int i = 0; i < taskListEditText.size() - 1; i++){
                tableContainer.removeView(taskListEditText.get(i));
            }
        }
        startActivity(new Intent(EditTaskActivity.this, HomePropertyTabsActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
