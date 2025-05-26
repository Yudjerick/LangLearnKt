package com.example.langlearnkt.data

import com.example.langlearnkt.data.entities.Lesson
import com.example.langlearnkt.data.entities.LessonContent
import com.example.langlearnkt.data.entities.LessonMetaData
import com.example.langlearnkt.data.entities.OrderTask
import com.example.langlearnkt.data.entities.ParagraphData
import com.example.langlearnkt.data.entities.TitleParagraphTask

val lesson1 = Lesson(
    LessonMetaData("", "Test Lesson 1", "Mock description of lesson"),
    LessonContent(
        "",
        listOf(
            OrderTask("","", listOf("we", "are", "the", "champions"), listOf("losers")),
            TitleParagraphTask(
                listOf(
                    ParagraphData(
                        "(A) In the early 2000s, scientists first noticed unusual temperature patterns in the region. " +
                                "At first, the changes were small—slightly warmer winters, earlier springs—but over time, " +
                                "the shifts became impossible to ignore. By 2010, it was clear that this was not normal " +
                                "seasonal variation but part of a much larger trend.",
                        "A",
                        "The Origins of the Problem"
                    ),
                    ParagraphData(
                        "(B) As temperatures rose, farmers struggled with unpredictable growing seasons. Some crops " +
                                "bloomed too early and were damaged by late frosts, while others failed entirely due to drought. " +
                                "Meanwhile, coastal towns faced increased flooding, forcing some residents to relocate. " +
                                "Everyday life became more uncertain.",
                        "B",
                        "Immediate Effects on Daily Life"
                    ),
                    ParagraphData(
                        "(C) Researchers discovered that ocean currents were shifting due to melting polar ice. This " +
                                "altered weather patterns worldwide, creating a chain reaction. Warmer air held more moisture, " +
                                "leading to stronger storms, while other areas experienced extreme heatwaves. The science was " +
                                "complex, but the evidence was undeniable.",
                        "C",
                        "Scientific Explanations Behind the Phenomenon"
                    ),
                    ParagraphData(
                        "(D) Local governments began building flood barriers and updating infrastructure, but funding " +
                                "was limited. On a national level, debates raged over whether to prioritize short-term relief " +
                                "or long-term prevention. Some policies helped, but many argued they were too little, too late.",
                        "D",
                        "Government Responses and Policies"
                    ),
                    ParagraphData(
                        "(E) In response, grassroots movements emerged. Neighborhoods developed emergency plans, and " +
                                "farmers experimented with drought-resistant crops. Schools taught children about conservation, " +
                                "and families adopted new habits to reduce waste. People realized that waiting for official " +
                                "solutions wasn't an option.",
                        "E",
                        "How Communities Are Adapting"
                    ),
                    ParagraphData(
                        "(F) If current trends continue, experts warn of mass migrations, food shortages, and economic " +
                                "instability. However, some believe technology and global cooperation could still mitigate the " +
                                "worst outcomes. The next decade will be decisive in determining whether adaptation is possible—" +
                                "or if irreversible damage is unavoidable.",
                        "F",
                        "Predictions for the Future"
                    )
                )
            )
        )
    )
)