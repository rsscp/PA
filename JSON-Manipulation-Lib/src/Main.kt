import json.converter.convert
import json.rest.Server

import tests.Controller
import tests.Course
import tests.CourseController
import tests.EvalItem
import tests.EvalType


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val courses = listOf(Course(
        "PA", 6, listOf(
            EvalItem("quizzes", .2, false, null),
            EvalItem("project", .8, true, EvalType.PROJECT)
        )))
    Server(CourseController(courses)).start()
}