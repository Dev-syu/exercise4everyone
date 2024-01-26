package kr.co.ddoko.memberservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import kr.co.ddoko.memberservice.common.dto.MemberDto;
import kr.co.ddoko.memberservice.common.dto.TeamDto;
import kr.co.ddoko.memberservice.common.dto.TeamInvolveDto;
import kr.co.ddoko.memberservice.domain.embedded.Permission;
import kr.co.ddoko.memberservice.domain.embedded.Sex;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.team.Team;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import kr.co.ddoko.memberservice.domain.team.TeamRepository;
import kr.co.ddoko.memberservice.exception.team.DuplicateTeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    public MemberService memberService;
    @Autowired
    public TeamInvolveService teamInvolveService;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }



    public Team saveTeam(TeamDto.SaveRequest saveRequest) {
        if (isDuplicateTeam(saveRequest)) {
            throw new DuplicateTeamException("이미 존재하는 팀입니다: " + saveRequest.getTitle());
        }
        return teamRepository.save(saveRequest.toEntity());
    }

    public void removeTeam(TeamDto.RemoveRequest removeRequest) {
        Optional<Team> existingTeam = findByTeam(removeRequest.getTitle(),removeRequest.getMaster(),removeRequest.getLocation());
        if(existingTeam.isPresent()){
            Team team = existingTeam.get();

            String teamInvolveJpql = "SELECT m.member.mid FROM TeamInvolve m WHERE m.team = :team";
            List<Long> memberIds = em.createQuery(teamInvolveJpql, Long.class)
                    .setParameter("team", team)
                    .getResultList();

            String memberJpql = "SELECT m FROM Member m WHERE m.mid IN :memberIds";
            List<Member> updateList = em.createQuery(memberJpql, Member.class)
                    .setParameter("memberIds", memberIds)
                    .getResultList();

            for (Member member : updateList) {
                MemberDto.ChargeRequest chargeRequest = MemberDto.ChargeRequest.builder()
                        .mid(member.getMid())
                        .id(member.getId())
                        .newPassword(member.getPassword())
                        .newName(member.getName())
                        .sex(member.getSex())
                        .newWeight(member.getWeight())
                        .birthDay(member.getBirthDay())
                        .newBelt(member.getBelt())
                        .newPhone(member.getPhone())
                        .newEmail(member.getEmail())
                        .newPermission(member.getPermission())
                        .newSleepAccount(member.isSleepAccount())
                        .newTeamInvolve(null)
                        .build();
                memberService.updateMemberInfo(chargeRequest);
            }

            String jpql = "SELECT t FROM TeamInvolve t WHERE t.member.mid IN :memberIds";
            List<TeamInvolve> teamInvolves = em.createQuery(jpql, TeamInvolve.class)
                    .setParameter("memberIds", memberIds)
                    .getResultList();
            for(TeamInvolve teamInvolve : teamInvolves){
                TeamInvolveDto.ChangeRequest changeRequest = TeamInvolveDto.ChangeRequest.builder()
                        .id(teamInvolve.getId())
                        .newTeam(null)
                        .newState(null)
                        .member(null)
                        .build();

                teamInvolveService.updateTeamInvolve(changeRequest);


                Optional<TeamInvolve> opt= teamInvolveService.findByid(changeRequest.getId());
                if(opt.isPresent()){
                    TeamInvolve gTeamInvolve = opt.get();
                    TeamInvolveDto.RemoveRequest involveRemoveRequest = TeamInvolveDto.RemoveRequest.builder()
                            .id(gTeamInvolve.getId())
                            .team(gTeamInvolve.getTeam())
                            .member(gTeamInvolve.getMember())
                            .state(gTeamInvolve.getState())
                            .build();
                    teamInvolveService.removeTeamInvolve(involveRemoveRequest);

                }
            }
            em.remove(team);

        }
//        existingTeam.ifPresent(team -> em.remove(team));
    }

    public Optional<Team> findByTeam(String title, String master, String location) {
        try {
            String jpql = "SELECT m FROM Team m WHERE m.title = :title and m.master = :master and m.location = :location";

            return em.createQuery(jpql, Team.class)
                    .setParameter("title", title)
                    .setParameter("master",master)
                    .setParameter("location",location)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private boolean isDuplicateTeam(TeamDto.SaveRequest team) {
        Optional<Team> existingTeam = findByTeam(team.getTitle(),team.getMaster(),team.getLocation());
        return existingTeam.isPresent();
    }
}
