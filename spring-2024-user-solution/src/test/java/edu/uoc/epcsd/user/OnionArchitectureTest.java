package edu.uoc.epcsd.user;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;


@AnalyzeClasses(
        packages = {"edu.uoc.epcsd.user"},
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class}
)
class OnionArchitectureTest {

    /**
     * Test fails as method createAlert() from AlertServiceImpl references class object GetProductResponse,
     * breaking the onion architecture layering rules. We know that neither the domain nor the application packages
     * must contain dependencies on any adapter package.
     * @param classes java classes to be analysed.
     */
    @ArchTest
    void checkOnionArchitecture_isRespected (JavaClasses classes) {
        classes.forEach(c -> System.out.println(c.getSimpleName()));

        onionArchitecture()
                // packages contain the domain entities
                .domainModels("..domain..")
                // contains services that use the entities in the domainModel packages.
                .domainServices("..domain.service..")
                // packages contain services and configuration to run the application and use cases
                .applicationServices("..")
                // package contains logic to connect to external systems and/or infrastructure
                .adapter("persistence", "..infrastructure.repository..")
                .adapter("rest", "..application.rest..")
                .check(classes);
    }
}
