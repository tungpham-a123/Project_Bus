package vn.edu.schoolname.schoolbusmanagementsystem.model;

import java.util.Date;

public class Registration {

    private int id;
    private Student student;
    private Route route;
    private Stop pickupStop;
    private Date registrationDate;
    private String status;
    private Trip todaysTrip;
    private Attendance attendanceStatus;

    public Registration() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Stop getPickupStop() {
        return pickupStop;
    }

    public void setPickupStop(Stop pickupStop) {
        this.pickupStop = pickupStop;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Trip getTodaysTrip() {
        return todaysTrip;
    }

    public void setTodaysTrip(Trip todaysTrip) {
        this.todaysTrip = todaysTrip;
    }

    public Attendance getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(Attendance attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }
}
