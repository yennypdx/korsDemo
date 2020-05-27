package com.android.kors.helperClasses;

import com.android.kors.models.ApprovedNegotiation;
import com.android.kors.models.Assignment;
import com.android.kors.models.ListModel;
import com.android.kors.models.Negotiation;
import com.android.kors.models.SubmissionUpdate;
import com.android.kors.models.TempData;
import com.android.kors.models.User;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Communicator {

    private static Retrofit retrofit;
    private static final String KORS_SERVER_URL = "https://korswebapi.azurewebsites.net/";
    //private static final String HOME_SERVER_URL = "https://localhost:5000/";

    public static Retrofit getRetrofitInstance(){
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, to get connection logs
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(KORS_SERVER_URL)
                    .build();
        }
        return retrofit;
    }

    /** -------------------------------------------------------------- related requests: TEMPDATA */
    public void postAllTablesToServer(TempData tempData, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertAllTempDataToServer(tempData);
        call.enqueue(callback);
    }

    /** ------------------------------------------------------------------ related requests: USER */
    public void getIsUserExist(String email, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getConfirmationUserExist(email);
        call.enqueue(callback);
    }

    public void postInsertInitUserData(String name, String email, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertUser(name, email);
        call.enqueue(callback);
    }

    public void updateUserPosition(String email, String position, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.updateUserPosition(email, position);
        call.enqueue(callback);
    }

    public void updateUserName(String email, String name, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.updateUserName(email, name);
        call.enqueue(callback);
    }

    public void updateUserPhone(String email, String phone, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.updateUserPhone(email, phone);
        call.enqueue(callback);
    }

    public void postInsertInitMemberUserData(User user, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertMemberUser(user);
        call.enqueue(callback);
    }

    public void getOneUserNameByUserId(int uid, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getOneUserNameByUserId(uid);
        call.enqueue(callback);
    }

    public void getUserIdByEmail(String email, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getUserIdByEmail(email);
        call.enqueue(callback);
    }

    public void getUserIdByName(String name, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getUserIdByName(name);
        call.enqueue(callback);
    }

    public void getTotalUserCount(Callback<Long> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Long> call = service.getUserCount();
        call.enqueue(callback);
    }

    public void getUserNameAndPositionList(Callback<List<User>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<User>> call = service.GetUserNameAndPosList();
        call.enqueue(callback);
    }

    public void getUserNameListFromSpecificGroup(int gid, Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getUserNameListFromSpecificGroup(gid);
        call.enqueue(callback);
    }

    public void getOneUserNameByEmail(String email, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getOneUserNameByEmail(email);
        call.enqueue(callback);
    }

    public void getUserPositionByEmail(String email, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getUserPosition(email);
        call.enqueue(callback);
    }

    public void getUserNameListVersionOnePointZero(Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getUserNameListVerOnePointZero();
        call.enqueue(callback);
    }

    public void getUserPhoneNumberById(int uid, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getUserPhoneNumber(uid);
        call.enqueue(callback);
    }

    public void getAdminPhone(Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getAdminPhone();
        call.enqueue(callback);
    }

    public void getAdminEmailListFromServer(Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getAdminEmailList();
        call.enqueue(callback);
    }

    public void getUserEmailByUserId(int uid, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getUserEmailById(uid);
        call.enqueue(callback);
    }

    public void deleteUserById(int uid, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.deleteUserById(uid);
        call.enqueue(callback);
    }

    /** ----------------------------------------------------------------- related requests: GROUP */
    public void postInsertGroupName(String name, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertGroupName(name);
        call.enqueue(callback);
    }

    public void postInsertGroupQrCode(String name, byte[] code, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.insertGroupQrCode(name, code);
        call.enqueue(callback);
    }

    public void getGroupIdByGroupName(String name, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getGroupIdByGroupName(name);
        call.enqueue(callback);
    }

    public void getGroupNameByGroupId(int id, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getGroupNameByGroupId(id);
        call.enqueue(callback);
    }

    public void getGroupQrCodeByGroupId(int id, Callback<byte[]> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<byte[]> call = service.getGroupQrCodeByGroupId(id);
        call.enqueue(callback);
    }

    /** ------------------------------------------------------------------ related requests: ROOM */
    public void postInsertRoomName(String room, int groupId, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertRoomName(room, groupId);
        call.enqueue(callback);
    }

    public void getRoomIdByRoomName(String room, int gid, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getRoomIdByRoomName(room, gid);
        call.enqueue(callback);
    }

    public void getRoomNameByRoomId(int rid, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getRoomNameByRoomId(rid);
        call.enqueue(callback);
    }

    public void getRoomNameListByGroupId(int gid, Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getRoomListByGroupId(gid);
        call.enqueue(callback);
    }

    public void postAddRoomName(List<String> roomsToAdd, int groupId, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.addRoomName(groupId, roomsToAdd);
        call.enqueue(callback);
    }

    public void updateEditRoomName(int groupId, Map<String,String> newRoomNames, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.updateRoomName(groupId, newRoomNames);
        call.enqueue(callback);
    }

    public void deleteRoomName(int gid, List<String> list, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.deleteRoomName(gid, list);
        call.enqueue(callback);
    }

    /** ------------------------------------------------------------------ related requests: TASK */
    public void postInsertTaskName(String task, int groupId, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertTaskName(task, groupId);
        call.enqueue(callback);
    }

    public void getTaskIdByTaskName(String task, int gid, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getTaskIdByTaskName(task, gid);
        call.enqueue(callback);
    }

    public void getTaskNameByTaskId(int tid, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.getTaskNameByTaskId(tid);
        call.enqueue(callback);
    }

    public void getTaskNameListByGroupId(int gid, Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getTaskListByGroupId(gid);
        call.enqueue(callback);
    }

    public void postAddTaskName(List<String> tasksToAdd, int groupId, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.addTaskName(groupId, tasksToAdd);
        call.enqueue(callback);
    }

    public void updateEditTaskName(int groupId, Map<String,String> newTaskNames, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.updateTaskName(groupId, newTaskNames);
        call.enqueue(callback);
    }

    public void deleteTaskName(int gid, List<String> list, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.deleteTaskName(gid, list);
        call.enqueue(callback);
    }

    /** ------------------------------------------------------------- related requests: USERGROUP */
    public void postInsertUserGroupPair(int userid, int groupid, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertUserGroupPair(userid, groupid);
        call.enqueue(callback);
    }

    public void getGroupListByUserId(int id, Callback<List<Integer>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<Integer>> call = service.getGroupListByUserId(id);
        call.enqueue(callback);
    }

    public void getGroupIdByUserId(int id, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getGroupIdByUserId(id);
        call.enqueue(callback);
    }

    public void deleteUserGroupPair(int uid, int gid, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.deleteUserByPair(uid, gid);
        call.enqueue(callback);
    }

    /** ------------------------------------------------------------ related requests: ASSIGNMENT */
    public void postInsertAssignment(Assignment assignment, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertNewAssignment(assignment);
        call.enqueue(callback);
    }

    public void updateSingleAssignmentStatus(int id, String newStatus, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.updateStatusOfOneAssignment(id, newStatus);
        call.enqueue(callback);
    }

    public void updateSubmissionDateTimeStatus(SubmissionUpdate update, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.updateSubmissionDateTimeStatus(update);
        call.enqueue(callback);
    }

    public void updateApprovedNewDateTimeStatus(ApprovedNegotiation update, Callback<String> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<String> call = service.updateApprovedDateTimeStatus(update);
        call.enqueue(callback);
    }

    public void getAssignmentListByStatusName(String statName, String date1, String date2, Callback<List<Assignment>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<Assignment>> call = service.getAssignmentListByStatusName(statName, date1, date2);
        call.enqueue(callback);
    }

    public void getAssignmentListByUserIdWithinInterval(int id, String date1, String date2,
                                                          Callback<List<Assignment>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<Assignment>> call = service.getAssignmentListByUserId(id, date1, date2);
        call.enqueue(callback);
    }

    public void getAssignmentListByCompletionDate(String compDate, Callback<List<Assignment>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<Assignment>> call = service.getAssignmentListByCompletionDate(compDate);
        call.enqueue(callback);
    }

    public void getAssignmentIdListByUid(int UserId, Callback<List<Integer>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<Integer>> call = service.getAssignmentListByUid(UserId);
        call.enqueue(callback);
    }

    public void getSingleAssignmentByAssignmentId(int aid, Callback<Assignment> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Assignment> call = service.getOneAssignmentByAsgId(aid);
        call.enqueue(callback);
    }

    public void getListOfDatesWithinInterval(String date1, String date2, Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getListOfDatesWithinInterval(date1, date2);
        call.enqueue(callback);
    }

    public void getAssignmentListBasedOnStatus(String status, Callback<List<Assignment>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<Assignment>> call = service.getListOfAssignmentBasedOnStatus(status);
        call.enqueue(callback);
    }

    public void getMonthlyListOfDatesToMark(String month, String year, Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getMonthlyListOfDates(month, year);
        call.enqueue(callback);
    }

    public void getTotalNegotiatingCount(Callback<Long> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Long> call = service.getNegotiatingAsgCountCount();
        call.enqueue(callback);
    }

    public void getTotalCompletedAsgCount(Callback<Long> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Long> call = service.getCompletedAsgCountCount();
        call.enqueue(callback);
    }

    public void deleteAssignment(int uid, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.deleteAssignmentByUserId(uid);
        call.enqueue(callback);
    }

    /** ----------------------------------------------------------------- related requests: IMAGE */
    public void uploadImageByAssignment(int id, String image, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.uploadImage(id, image);
        call.enqueue(callback);
    }

    public void getListOfImagesForOneAssignment(int id, Callback<List<String>> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<List<String>> call = service.getListOfImagesFromOneAsg(id);
        call.enqueue(callback);
    }

    public void getImageCountForOneAssignment(int id, Callback<Long> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Long> call = service.getImgCountFromOneAssignment(id);
        call.enqueue(callback);
    }

    /** ----------------------------------------------------------- related requests: NEGOTIATION */
    public void postNegotiationData(Negotiation negotiation, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.insertNegotiation(negotiation);
        call.enqueue(callback);
    }

    public void getNegotiationData(int assignmentId, Callback<Negotiation> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Negotiation> call = service.getNegotiation(assignmentId);
        call.enqueue(callback);
    }

    public void deleteNegotiation(List<Integer> aidList, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.deleteNegotiation(aidList);
        call.enqueue(callback);
    }

    /** --------------------------------------------------------- related requests: TEXT MESSAGES */
    public void postTextMessage(String number, String msg, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.textMessage(number, msg);
        call.enqueue(callback);
    }

    /** -------------------------------------------------------- related requests: EMAIL MESSAGES */
    public void postEmailToAdmins(List<String> emails, String msg, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.emailMessageAdmin(emails, msg);
        call.enqueue(callback);
    }

    public void postEmailToMember(String email, String msg, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.emailMessageMember(email, msg);
        call.enqueue(callback);
    }

    /** ------------------------------------------------------- related requests: USER NOTIF PREF */
    public void postNotifPref(int userid, int prefid, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.postUserNotifPreference(userid, prefid);
        call.enqueue(callback);
    }

    public void updateUserNotifPref(int userid, int prefid, Callback<Void> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Void> call = service.updateUserNotifPref(userid, prefid);
        call.enqueue(callback);
    }

    public void getCurrentUserNotifPref(int userid, Callback<Integer> callback){
        getRetrofitInstance();
        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Integer> call = service.getUserNotifPreference(userid);
        call.enqueue(callback);
    }

}
