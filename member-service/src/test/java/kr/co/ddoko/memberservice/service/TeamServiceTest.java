package kr.co.ddoko.memberservice.service;
import kr.co.ddoko.memberservice.common.dto.TeamDto;
import kr.co.ddoko.memberservice.domain.team.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamServiceTest {
    @Autowired
    private TeamService teamService;
    @Test
    @Rollback(value = false)
    void saveTeam테스트() {
        // Given
        TeamDto.SaveRequest saveRequest = TeamDto.SaveRequest.builder()
                .title("hive")
                .master("김승훈")
                .location("창원")
                .build();

        //When
        Team savedTeam = teamService.saveTeam(saveRequest);
        Optional<Team> optionalTeam = teamService.findByTeam("hive", "김승훈", "창원");

        // Then
        assertNotNull(savedTeam.getTid(), "Saved team should have an ID");
        assertTrue(optionalTeam.isPresent(), "Team should be found");

        Team findTeam = optionalTeam.get();

        assertEquals(savedTeam.getTitle(), findTeam.getTitle(), "Titles should match");
        assertEquals(savedTeam.getMaster(), findTeam.getMaster(), "Masters should match");
        assertEquals(savedTeam.getLocation(), findTeam.getLocation(), "Locations should match");

        assertEquals(savedTeam, findTeam, "Teams should be equal");

    }

    @Test
//    @Rollback(value = false)
    void removeTeam() {
        // Arrange
        Optional<Team> optionalTeam = teamService.findByTeam("New Team", "John Doe", "Test Location");

        // Assert
        assertTrue(optionalTeam.isPresent(), "팀이 존재해야 합니다.");

        // Act
        TeamDto.RemoveRequest removeRequest = TeamDto.RemoveRequest.builder()
                .title(optionalTeam.get().getTitle())
                .master(optionalTeam.get().getMaster())
                .location(optionalTeam.get().getLocation())
                .build();
        teamService.removeTeam(removeRequest);

        // Assert
        optionalTeam = teamService.findByTeam("New Team", "John Doe", "Test Location");
        assertFalse(optionalTeam.isPresent(), "팀이 삭제되어야 합니다.");
    }
}