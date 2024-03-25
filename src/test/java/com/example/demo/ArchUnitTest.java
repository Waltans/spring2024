package com.example.demo;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchUnitTest {
    final String PATH = "com.example.demo.";
    private final JavaClasses classes = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.example.demo");

    @Test
    @DisplayName("Проверка на слоеную архитектуру")
    void LayeredArchitectureTest() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy(PATH + "domain..")
                .layer("app").definedBy(PATH + "app..")
                .layer("extern").definedBy(PATH + "extern..").as("optional")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("domain", "app", "extern")
                .whereLayer("app").mayOnlyBeAccessedByLayers("app", "extern")
                .whereLayer("extern").mayNotBeAccessedByAnyLayer()
                .check(classes);
    }
}
