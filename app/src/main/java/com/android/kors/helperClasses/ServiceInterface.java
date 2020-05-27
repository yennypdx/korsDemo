package com.android.kors.helperClasses;

import com.android.kors.models.ApprovedNegotiation;
import com.android.kors.models.Assignment;
import com.android.kors.models.ListModel;
import com.android.kors.models.Negotiation;
import com.android.kors.models.SubmissionUpdate;
import com.android.kors.models.TempData;
import com.android.kors.models.User;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceInterface {

    /** -------------------------------------------------------------- related requests: TEMPDATA */
    @POST("tempdata/ins")
    Call<Void> insertAllTempDataToServer(
            @Body TempData tempData
    );

    /** ------------------------------------------------------------------ related requests: USER */
    @GET("user/finduser/email")
    Call<String> getConfirmationUserExist(
            @Query("email") String Email
    );

    @FormUrlEncoded
    @POST("user/ins")
    Call<Void> insertUser(
         @Field("name") String UserName,
         @Field("email") String Email
    );

    @FormUrlEncoded
    @PATCH("user/update/pos")
    Call<Void> updateUserPosition(
        @Field("email") String Email,
        @Field("position") String Position
    );

    @FormUrlEncoded
    @PATCH("user/update/name")
    Call<String> updateUserName(
            @Field("email") String Email,
            @Field("name") String UserName
    );

    @PATCH("user/update/phone")
    Call<String> updateUserPhone(
            @Field("email") String Email,
            @Field("phone") String Phone
    );

    @POST("user/ins/member")
    Call<Void> insertMemberUser(
            @Body User user
    );

    @GET("user/get/name/id")
    Call<String> getOneUserNameByUserId(
        @Query("userId") int Id
    );

    @GET("user/get/id/email")
    Call<Integer> getUserIdByEmail(
        @Query("email") String Email
    );

    @GET("user/get/id/name")
    Call<Integer> getUserIdByName(
            @Query("name") String Name
    );

    @GET("user/count")
    Call<Long> getUserCount();

    @GET("user/get/namepos/list")
    Call<List<User>> GetUserNameAndPosList();

    @GET("user/get/name/list")
    Call<List<String>> getUserNameListFromSpecificGroup(
            @Query("groupId") int GroupId
    );

    @GET("user/get/one/name")
    Call<String> getOneUserNameByEmail(
        @Query("email") String Email
    );

    @GET("user/get/pos")
    Call<String> getUserPosition(
        @Query("email") String Email
    );

    @GET("user/get/name/list/ver1")
    Call<List<String>> getUserNameListVerOnePointZero();

    @GET("user/get/phn/id")
    Call<String> getUserPhoneNumber(
        @Query("uid") int Id
    );

    @GET("user/get/adminphn")
    Call<String> getAdminPhone();

    @GET("user/get/adm/milist")
    Call<List<String>> getAdminEmailList();

    @GET("user/get/email/id")
    Call<String> getUserEmailById(
            @Query("uid") int Id
    );

    @DELETE("user/del")
    Call<Void> deleteUserById(
        @Query("uid") int UserId
    );

    /** ----------------------------------------------------------------- related requests: GROUP */
    @FormUrlEncoded
    @POST("group/ins/name")
    Call<Void> insertGroupName(
        @Field("groupName") String GroupName
    );

    @FormUrlEncoded
    @POST("group/ins/code")
    Call<String> insertGroupQrCode(
        @Field("groupName") String GroupName,
        @Field("qrcode") byte[] QrCode
    );

    @GET("group/get/id")
    Call<Integer> getGroupIdByGroupName(
        @Query("groupName") String GroupName
    );

    @GET("group/get/name")
    Call<String> getGroupNameByGroupId(
        @Query("groupId") int Id
    );

    @GET("group/get/qr")
    Call<byte[]> getGroupQrCodeByGroupId(
        @Query("groupId") int Id
    );

    /** ------------------------------------------------------------ related requests: ROOM */
    @FormUrlEncoded
    @POST("room/ins")
    Call<Void> insertRoomName(
        @Field("roomName") String RoomName,
        @Field("groupId") int GroupId
    );

    @GET("room/get/id/name")
    Call<Integer> getRoomIdByRoomName(
        @Query("roomName") String RoomName,
        @Query("groupId") int GroupId
    );

    @GET("room/get/name/id")
    Call<String> getRoomNameByRoomId(
        @Query("roomId") int Id
    );

    @GET("room/get/list")
    Call<List<String>> getRoomListByGroupId(
        @Query("groupId") int GroupId
    );

    @POST("room/add/{gid}/")
    Call<Void> addRoomName(
            @Path("gid") int GroupId,
            @Body List<String> Names
    );

    @PATCH("room/update/{gid}/")
    Call<Void> updateRoomName(
            @Path("gid") int GroupId,
            @Body Map<String,String> Names
    );

    @DELETE("room/del")
    Call<Void> deleteRoomName(
            @Query("gid") int GroupId,
            @Query("list") List<String> Names
    );

    /** ------------------------------------------------------------ related requests: TASK */
    @FormUrlEncoded
    @POST("task/ins")
    Call<Void> insertTaskName(
        @Field("taskName") String TaskName,
        @Field("groupId") int GroupId
    );

    @GET("task/get/id/name")
    Call<Integer> getTaskIdByTaskName(
        @Query("taskName") String TaskName,
        @Query("groupId") int GroupId
    );

    @GET("task/get/name/id")
    Call<String> getTaskNameByTaskId(
        @Query("taskId") int Id
    );

    @GET("task/get/list")
    Call<List<String>> getTaskListByGroupId(
        @Query("groupId") int GroupId
    );

    @POST("task/add/{gid}/")
    Call<Void> addTaskName(
            @Path("gid") int GroupId,
            @Body List<String> Names
    );

    @PATCH("task/update/{gid}/")
    Call<Void> updateTaskName(
            @Path("gid") int GroupId,
            @Body Map<String,String> Names
    );

    @DELETE("task/del")
    Call<Void> deleteTaskName(
            @Query("gid") int GroupId,
            @Query("list") List<String> Names
    );

    /** ------------------------------------------------------------- related requests: USERGROUP */
    @FormUrlEncoded
    @POST("usergroup/ins")
    Call<Void> insertUserGroupPair(
        @Field("userId") int UserId,
        @Field("groupId") int GroupId
    );

    @GET("usergroup/get/gidlist/uid")
    Call<List<Integer>> getGroupListByUserId(
        @Query("userId") int UserId
    );

    @GET("usergroup/get/gid/uid")
    Call<Integer> getGroupIdByUserId(
        @Query("userId") int UserId
    );

    @DELETE("usergroup/del")
    Call<Void> deleteUserByPair(
            @Field("uid") int UserId,
            @Field("gid") int GroupId
    );

    /** ------------------------------------------------------------ related requests: ASSIGNMENT */
    @POST("assignment/ins")
    Call<Void> insertNewAssignment(
        @Body Assignment assignment
    );

    @PATCH("assignment/update/stat/{aid}")
    Call<String> updateStatusOfOneAssignment(
        @Path("aid") int Id,
        @Query("status") String Status
    );

    @PATCH("assignment/update/submission")
    Call<String> updateSubmissionDateTimeStatus(
        @Body SubmissionUpdate update
    );

    @PATCH("assignment/update/approved/nego")
    Call<String> updateApprovedDateTimeStatus(
            @Body ApprovedNegotiation update
    );

    @GET("assignment/get/asglist/statname")
    Call<List<Assignment>> getAssignmentListByStatusName(
        @Query("status") String Status,
        @Query("dStart") String DateCreated,
        @Query("dEnd") String DateCompleted
    );

    @GET("assignment/get/asglist/userid")
    Call<List<Assignment>> getAssignmentListByUserId(
        @Query("userId") int UserId,
        @Query("dateStarted") String DateCreated,
        @Query("dateCompleted") String DateCompleted
    );

    @GET("assignment/get/asglist/dcomp")
    Call<List<Assignment>> getAssignmentListByCompletionDate(
        @Query("dEnd") String DateCompleted
    );

    @GET("assignment/get/asglist/uid")
    Call<List<Integer>> getAssignmentListByUid(
            @Query("uid") int UserId
    );

    @GET("assignment/get/sglasg/id")
    Call<Assignment> getOneAssignmentByAsgId(
        @Query("aid") int Id
    );

    @GET("assignment/get/dates")
    Call<List<String>> getListOfDatesWithinInterval(
        @Query("dStart") String DateCreated,
        @Query("dEnd") String DateCompleted
    );

    @GET("assignment/get/asglist/status")
    Call<List<Assignment>> getListOfAssignmentBasedOnStatus(
            @Query("status") String status
    );

    @GET("assignment/get/monthly/dates")
    Call<List<String>> getMonthlyListOfDates(
            @Query("month") String Month,
            @Query("year") String Year
    );

    @GET("assignment/get/completed/count")
    Call<Long> getCompletedAsgCountCount();

    @GET("assignment/get/negotiating/count")
    Call<Long> getNegotiatingAsgCountCount();

    @DELETE("assignment/del")
    Call<Void> deleteAssignmentByUserId(
            @Field("uid") int UserId
    );

    /** ----------------------------------------------------------------- related requests: IMAGE */
    @FormUrlEncoded
    @POST("image/upload")
    Call<Void> uploadImage(
        @Field("aid") int AssignmentId,
        @Field("image") String Photo
    );

    @GET("image/get/sglasglist/id")
    Call<List<String>> getListOfImagesFromOneAsg(
        @Query("id") int AssignmentId
    );

    @GET("image/get/img/count")
    Call<Long> getImgCountFromOneAssignment(
            @Query("aid") int AssignmentId
    );

    /** ----------------------------------------------------------- related requests: NEGOTIATION */
    @POST("negotiation/ins")
    Call<Void> insertNegotiation(
            @Body Negotiation negotiation
    );

    @GET("negotiation/get")
    Call<Negotiation> getNegotiation(
            @Query("aid") int AssignmentId
    );

    @DELETE("negotiation/del")
    Call<Void> deleteNegotiation(
            @Field("aidList") List<Integer> AssignmentIdList
    );

    /** --------------------------------------------------------- related requests: TEXT MESSAGES */
    @FormUrlEncoded
    @POST("text/send")
    Call<Void> textMessage(
            @Field("destinationNumber") String Phone,
            @Field("message") String Message
    );

    /** -------------------------------------------------------- related requests: EMAIL MESSAGES */
    @FormUrlEncoded
    @POST("email/send/adm")
    Call<Void> emailMessageAdmin(
            @Field("adminEmail") List<String> Emails,
            @Field("message") String Message
    );

    @FormUrlEncoded
    @POST("email/send/mbr")
    Call<Void> emailMessageMember(
            @Field("memberEmail") String Email,
            @Field("message") String Message
    );

    /** ------------------------------------------------------- related requests: USER NOTIF PREF */
    @FormUrlEncoded
    @POST("notification/ins")
    Call<Void> postUserNotifPreference(
            @Field("uid") int UserId,
            @Field("prefid") int UserNotifPrefId
    );

    // update user's notif preference
    @PATCH("notification/update/pref")
    Call<Void> updateUserNotifPref(
            @Field("uid") int UserId,
            @Field("prefid") int UserNotifPrefId
    );

    // check user's preference
    @GET("notification/get/pref")
    Call<Integer> getUserNotifPreference(
            @Query("uid") int UserId
    );
}
