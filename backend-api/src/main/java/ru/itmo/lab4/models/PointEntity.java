package ru.itmo.lab4.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity(name = "points")
@NamedQuery(name = PointEntity.FIND_ALL_BY_USER, query = "from points where user = :user order by id asc")
public class PointEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL_BY_USER = "points.findByUser";

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double x, y, r;
    private Boolean result;
    private LocalDateTime createTime;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public PointEntity() {
        this.createTime = LocalDateTime.now();
    }

    public PointEntity(double x, double y, double r, UserEntity user) {
        this();
        this.x = x;
        this.y = y;
        this.r = r;
        this.user = user;
        checkInsideFunc();
    }

    public PointEntity(double x, double y, double r, boolean result, LocalDateTime createTime, UserEntity user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.createTime = createTime;
        this.user = user;
        checkInsideFunc();
    }

    public void checkInsideFunc() {
        if (x == null || y == null || r == null)
            throw new NullPointerException();

        this.result = (x >= 0 && y >= 0 && y <= -(2*x) + r) //linear function
                || (y >= 0 && x <= 0 && x >= -Math.sqrt(r * r - y * y)) //circular function
                || (y <= 0 && x <= 0 && y >= -r && x >= -r); //lines on r
    }

//    public String getCreatedTimeFormatted() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy 'at' HH:mm:ss a");
//        return createTime.format(formatter);
//    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getX() {
        return x;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public Double getY() {
        return y;
    }
    public void setY(Double y) {
        this.y = y;
    }
    public Double getR() {
        return r;
    }
    public void setR(Double r) {
        this.r = r;
    }
    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointEntity point = (PointEntity) o;
        return Objects.equals(x, point.x) &&
                Objects.equals(y, point.y) &&
                Objects.equals(r, point.r) &&
                Objects.equals(result, point.result) &&
                Objects.equals(user, point.user) &&
                Objects.equals(createTime, point.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r, result, user, createTime);
    }

    @Override
    public String toString() {
        return "PointEntity{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", result=" + result +
                ", owner=" + user.toString() +
                ", createTime=" + createTime +
                '}';
    }
}
