package iuh.fit.backend.resources;

import iuh.fit.backend.services.CompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/vi/companies")
public class CompanyResources {
    @Autowired
    private CompanyServices services;
}
