package mr.danceschool.controller.controllerModel;

import mr.danceschool.utils.DanceLevel;
import mr.danceschool.utils.Gender;
import mr.danceschool.utils.GroupStatus;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class GroupCourseModel {
    private Date createDate;
    private GroupStatus groupStatus;
    private List<Gender> genderList;
    private DanceLevel danceLevel;
    private long danceStyleId;
    private long locationId;
    private Set<Long> instructorIds;
    private DayOfWeek classroomDay;
    private LocalTime classroomStartTime;
    private int classroomDuration;

    public GroupCourseModel(Date createDate, GroupStatus groupStatus, List<Gender> genderList, DanceLevel danceLevel, long danceStyleId, long locationId, Set<Long> instructorIds, DayOfWeek classroomDay, LocalTime classroomStartTime, int classroomDuration) {
        this.createDate = createDate;
        this.groupStatus = groupStatus;
        this.genderList = genderList;
        this.danceLevel = danceLevel;
        this.danceStyleId = danceStyleId;
        this.locationId = locationId;
        this.instructorIds = instructorIds;
        this.classroomDay = classroomDay;
        this.classroomStartTime = classroomStartTime;
        this.classroomDuration = classroomDuration;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public GroupStatus getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(GroupStatus groupStatus) {
        this.groupStatus = groupStatus;
    }

    public List<Gender> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<Gender> genderList) {
        this.genderList = genderList;
    }

    public DanceLevel getDanceLevel() {
        return danceLevel;
    }

    public void setDanceLevel(DanceLevel danceLevel) {
        this.danceLevel = danceLevel;
    }

    public long getDanceStyleId() {
        return danceStyleId;
    }

    public void setDanceStyleId(long danceStyleId) {
        this.danceStyleId = danceStyleId;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public Set<Long> getInstructorIds() {
        return instructorIds;
    }

    public void setInstructorIds(Set<Long> instructorIds) {
        this.instructorIds = instructorIds;
    }

    public DayOfWeek getClassroomDay() {
        return classroomDay;
    }

    public void setClassroomDay(DayOfWeek classroomDay) {
        this.classroomDay = classroomDay;
    }

    public LocalTime getClassroomStartTime() {
        return classroomStartTime;
    }

    public void setClassroomStartTime(LocalTime classroomStartTime) {
        this.classroomStartTime = classroomStartTime;
    }

    public int getClassroomDuration() {
        return classroomDuration;
    }

    public void setClassroomDuration(int classroomDuration) {
        this.classroomDuration = classroomDuration;
    }
}

