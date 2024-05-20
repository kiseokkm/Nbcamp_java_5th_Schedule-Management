package com.sparta.nbcamp_java_5th_schedulemanagement.repository;

import com.sparta.nbcamp_java_5th_schedulemanagement.dto.ScheduleResponseDto;
import com.sparta.nbcamp_java_5th_schedulemanagement.dto.UpdateScheduleRequestDto;
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
        return new Schedule(id, schedule.getTitle(), schedule.getContents(), schedule.getManager(), schedule.getPassword(), schedule.getDate());
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

    public void updateSchedule(Long id, UpdateScheduleRequestDto updateScheduleRequestDto) {
        String sql = "UPDATE scheduleTable SET title = ?, contents = ?, manager = ?, date = ? WHERE id = ?";
        jdbcTemplate.update(sql, updateScheduleRequestDto.getTitle(), updateScheduleRequestDto.getContents(), updateScheduleRequestDto.getManager(), updateScheduleRequestDto.getDate(), id);
    }

    public Schedule findById(Long id) {
        String sql = "SELECT * FROM scheduleTable WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (resultSet, i) ->
                new Schedule(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        resultSet.getString("manager"),
                        resultSet.getString("password"),
                        resultSet.getString("date")
                )
        );
    }

    public void deleteSchedule(Long id) {
        String sql = "DELETE FROM scheduleTable WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}