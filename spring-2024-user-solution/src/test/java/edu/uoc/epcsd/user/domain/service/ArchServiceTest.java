package edu.uoc.epcsd.user.domain.service;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
        packages = "edu.uoc.epcsd.user.domain.service",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class}
)
class ArchServiceTest {

    @ArchTest
    void checkServiceAnnotatedClasses_haveSimpleNameEndingWithServiceImpl(JavaClasses classes) {
        classes().that()
                .areAnnotatedWith(Service.class)
                .should()
                .haveSimpleNameEndingWith("ServiceImpl")
                .check(classes);
    }
}