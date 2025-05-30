package com.example.langlearnkt.data

import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonContent
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.ParagraphData
import com.example.langlearnkt.data.entities.TitleParagraphTask

val lesson1 = Lesson(
    LessonMetaData("", "Food 1", "Learn how to talk about food. Part 1"),
    LessonContent(
        "",
        listOf(
            OrderTask("","Мне нравится итальянская кухня",
                listOf("I", "like", "italian", "cuisine"),
                listOf("hate", "You", "german", "kitchen")
            ),
            OrderTask("","Жареный рис",
                listOf("fried", "rice"),
                listOf("boiled", "wheat", "cooked", "noodles")
            ),
            OrderTask("","Мои любимые фрукты - ананасы",
                listOf("My", "favourite", "fruits", "are", "pineapples"),
                listOf("apples", "Your", "bananas", "vegetables", "nuts")
            )
        )
    )
)

val lesson2 = Lesson(
    LessonMetaData("", "Magnificent turtles", "Read text about turtles and complete task" ),
    LessonContent(
        id = "",
        listOf(
            TitleParagraphTask(
                listOf(
                    ParagraphData(
                        text = "(A) Turtles are among the oldest living reptiles, with ancestors dating back over 200 million years. They outlived the dinosaurs and have adapted to nearly every environment on Earth—from oceans to deserts. Their resilience is a testament to their incredible evolutionary design.",
                        letter = "A",
                        title = "Ancient Survivors"
                    ),
                    ParagraphData(
                        text = "(B) A turtle's shell is not just a home—it's part of its skeleton! Made of over 50 fused bones, the shell provides protection from predators. Contrary to popular belief, a turtle cannot crawl out of its shell—it’s permanently attached to its body.",
                        letter = "B",
                        title = "Built-In Armor"
                    ),
                    ParagraphData(
                        text = "(C) Turtles are famous for their slow pace, but this isn’t a weakness—it’s a survival strategy. Moving slowly helps them conserve energy, making them efficient survivors in harsh conditions. Some species, like the leatherback sea turtle, defy this stereotype by swimming thousands of miles across oceans!",
                        letter = "C",
                        title = "Slow and Steady Wins the Race"
                    ),
                    ParagraphData(
                        text = "(D) Many turtle species live exceptionally long lives. Some tortoises can live over 150 years, with the oldest recorded tortoise, Jonathan, reaching nearly 190 years old! Their slow metabolism and low-stress lifestyle contribute to their impressive lifespan.",
                        letter = "D",
                        title = "Longevity Champions"
                    ),
                    ParagraphData(
                        text = "(E) Sea turtles have an incredible sense of direction. They can detect Earth’s magnetic field, allowing them to migrate across vast oceans and return to the exact beach where they were born to lay their own eggs. Scientists are still unraveling the mysteries of their navigation skills.",
                        letter = "E",
                        title = "Masters of Navigation"
                    ),
                    ParagraphData(
                        text = "(F) Despite surviving for millions of years, many turtle species are now endangered due to habitat destruction, pollution, and poaching. Conservation efforts, like protecting nesting beaches and reducing plastic waste, are crucial to ensuring these remarkable creatures thrive for generations to come.",
                        letter = "F",
                        title = "Threatened by Human Activity"
                    )
                )
            )
        )
    )
)