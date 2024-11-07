package iuh.fit.backend.services;

import iuh.fit.backend.enums.SkillLevel;
import iuh.fit.backend.models.Candidate;
import iuh.fit.backend.models.Job;
import iuh.fit.backend.models.JobSkill;
import iuh.fit.backend.repositories.CandidateRepository;
import iuh.fit.backend.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateServices {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    public Page<Candidate> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidateRepository.findAll(pageable);//findFirst.../findTop...
    }

    public Page<Candidate> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Candidate> list;
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, candidates.size());
            list = candidates.subList(startItem, toIndex);
        }

        Page<Candidate> candidatePage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), candidates.size());

        return candidatePage;
    }

    public List<Candidate> findCandidatesForJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return job.getJobSkills().stream()
                .flatMap(jobSkill -> candidateRepository.findBySkillLevelAndSkillName(
                        jobSkill.getSkillLevel(), jobSkill.getSkill().getSkillName()).stream())
                .collect(Collectors.toList());
    }

    //suggestSkillToLearn
    public List<JobSkill> suggestSkillToLearn(Long candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        return jobRepository.findAll().stream()
                .flatMap(job -> job.getJobSkills().stream())
                .filter(jobSkill -> !candidate.getCandidateSkills().contains(jobSkill))
                .distinct()
                .collect(Collectors.toList());
    }


}