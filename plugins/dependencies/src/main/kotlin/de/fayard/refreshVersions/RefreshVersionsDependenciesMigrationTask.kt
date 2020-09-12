package de.fayard.refreshVersions

import de.fayard.refreshVersions.internal.configurationsWithHardcodedDepdencies
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class RefreshVersionsDependenciesMigrationTask : DefaultTask() {

    @TaskAction
    fun taskActionMigrate() {
        check(project == project.rootProject) { "This task is designed to run on root project only." }
        configurationsWithHardcodedDepdencies(project)
    }
}
