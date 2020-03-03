package com.agscom.file;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.agscom.file");

        noClasses()
            .that()
                .resideInAnyPackage("com.agscom.file.service..")
            .or()
                .resideInAnyPackage("com.agscom.file.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.agscom.file.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
