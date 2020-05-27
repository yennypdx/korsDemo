package com.android.kors;

import android.view.View;

import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.kors.activities.CompletionReqApprovalActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class ApprovalRequestActyImplementedTest {
    @Rule
    public ActivityTestRule<CompletionReqApprovalActivity> rule = new ActivityTestRule<>(CompletionReqApprovalActivity.class);

    // Check points:
    // making sure all items are present at run time
    @Test
    public void confirmCameraImgResultBoxIsPresent() throws Exception {
        CompletionReqApprovalActivity activity = rule.getActivity();
        View cameraResultById = activity.findViewById(R.id.cameraInputImgView);

        assertThat(cameraResultById, notNullValue());
    }

    @Test
    public void confirmCancelButtonIsPresent() throws Exception {
        CompletionReqApprovalActivity activity = rule.getActivity();
        View cancelBtnById = activity.findViewById(R.id.btnCameraCancel);

        assertThat(cancelBtnById, notNullValue());
    }

    @Test
    public void confirmTakePictureButtonIsPresent() throws Exception {
        CompletionReqApprovalActivity activity = rule.getActivity();
        View takePictureBtnById = activity.findViewById(R.id.btnCamera);

        assertThat(takePictureBtnById, notNullValue());
    }

    @Test
    public void confirmSendButtonIsPresent() throws Exception {
        CompletionReqApprovalActivity activity = rule.getActivity();
        View sendBtnById = activity.findViewById(R.id.btnCameraSend);

        assertThat(sendBtnById, notNullValue());
    }

}
