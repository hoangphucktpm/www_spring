package iuh.fit.frontend.controllers;

import iuh.fit.backend.models.Company;
import iuh.fit.backend.models.Job;
import iuh.fit.backend.repositories.CompanyRepository;
import iuh.fit.backend.repositories.JobRepository;
import iuh.fit.backend.repositories.JobSkillRepository;
import iuh.fit.backend.services.JobServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobServices jobServices;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("")
    public String showJobListPaging(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam(("search")) Optional<String> search,
                                    @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Job> jobPage = jobServices.findAll(currentPage - 1, pageSize, "id", "asc");

        if (search.isPresent()) {
            jobPage = jobServices.searchJobs(currentPage - 1, pageSize, search.get());
        }


        model.addAttribute("jobPage", jobPage);
        model.addAttribute("search", search.orElse(""));

        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            model.addAttribute("pageNumbers", IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList()));
        }
        return "jobs/list_job";
    }

    @GetMapping("/show-add-form")
    public ModelAndView showAddForm(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("job", new Job());
        modelAndView.setViewName("jobs/add_job");
        return modelAndView;
    }

    @PostMapping("/add")
    public String addJob(@ModelAttribute("job") Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "jobs/add_job";
        }
        jobRepository.save(job);
        return "redirect:/jobs";
    }

    @GetMapping("/show-edit-form/{id}")
    public ModelAndView showEditForm(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            modelAndView.addObject("job", job.get());
            modelAndView.setViewName("jobs/update_job");
        } else {
            modelAndView.setViewName("redirect:/jobs?error=jobNotFound");
        }
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateJob(@ModelAttribute("job") Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "jobs/update_job";
        }
        jobRepository.save(job);
        return "redirect:/jobs?success=updateSuccess";
    }

    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable("id") long id) {
        jobRepository.deleteById(id);
        return "redirect:/jobs?success=deleteSuccess";
    }

    @GetMapping("/view/{id}")
    public ModelAndView viewCompany(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            modelAndView.addObject("company", company.get());
            modelAndView.setViewName("jobs/view_company");
        } else {
            modelAndView.setViewName("redirect:/jobs?error=companyNotFound");
        }
        return modelAndView;
    }

    @GetMapping("/apply/{id}")
    public String applyJob(@PathVariable("id") long id) {
        return "redirect:/jobs?success=applySuccess";
    }
}