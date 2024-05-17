package com.sparta.nbcamp_java_5th_schedulemanagement.repository;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleRequestDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule saveSchedule(Schedule schedule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO scheduleTable (title, contents, manager, password, date) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, schedule.getTitle());
            preparedStatement.setString(2, schedule.getContents());
            preparedStatement.setString(3, schedule.getManager());
            preparedStatement.setString(4, schedule.getPassword());
            preparedStatement.setString(5, schedule.getDate());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        return schedule;
    }

    public List<ScheduleResponseDto> findAllSchedules() {
        String sql = "SELECT * FROM scheduleTable ORDER BY date DESC";
        return jdbcTemplate.query(sql, this::mapRowForSchedule);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        String sql = "SELECT * FROM scheduleTable WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowForSchedule);
    }

    private ScheduleResponseDto mapRowForSchedule(ResultSet resultSet, int i) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String contents = resultSet.getString("contents");
        String manager = resultSet.getString("manager");
        String date = resultSet.getString("date");

        return new ScheduleResponseDto(id, title, contents, manager, date);
    }

    public void updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        String sql = "UPDATE scheduleTable SET title = ?, contents = ?, manager = ? WHERE id = ?";
        jdbcTemplate.update(sql, scheduleRequestDto.getTitle(), scheduleRequestDto.getContents(), scheduleRequestDto.getManager(), id);
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM scheduleTable WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) -> {
            Schedule schedule = new Schedule();
            schedule.setId(resultSet.getLong("id"));
            schedule.setTitle(resultSet.getString("title"));
            schedule.setContents(resultSet.getString("contents"));
            schedule.setManager(resultSet.getString("manager"));
            schedule.setPassword(resultSet.getString("password"));
            schedule.setDate(resultSet.getString("date"));
            return schedule;
        });
    }
}