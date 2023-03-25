import com.lemitree.common.data.TreeItem
import com.lemitree.common.data.mapToTreeItems
import kotlin.test.Test
import kotlin.test.assertEquals

class TreeItemTest {
    @Test
    fun test() {
        val testResult = testPathsList.mapToTreeItems()
        assertEquals(testTreeItems, testResult)
    }
}

val testPathsList = listOf(
    "Being_-_Purpose_-_Meaningful_Life",
    "Being_-_Purpose_-_Meaningful_Life/Future_-_Aspirations",
    "Being_-_Purpose_-_Meaningful_Life/Past_-_Identity",
    "Being_-_Purpose_-_Meaningful_Life/Present_-_Action",
    "Being_-_Purpose_-_Meaningful_Life/Present_-_Action/Automation",
    "Being_-_Purpose_-_Meaningful_Life/Present_-_Action/Delegation-outsourcing",
    "Having_-_Resources_-_Means_to_Live",
    "Having_-_Resources_-_Means_to_Live/Education",
    "Having_-_Resources_-_Means_to_Live/Enjoyment",
    "Having_-_Resources_-_Means_to_Live/Health",
    "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health",
    "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Breathing",
    "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Coping_Mechanisms",
    "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Mindfulness",
    "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Mindfulness/Burn-out",
    "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Mindfulness/Depression",
    "Having_-_Resources_-_Means_to_Live/Health/Mental_Health",
    "Having_-_Resources_-_Means_to_Live/Health/Mental_Health/(External)_memory",
    "Having_-_Resources_-_Means_to_Live/Health/Mental_Health/Cognitive_Enhancement",
    "Having_-_Resources_-_Means_to_Live/Health/Mental_Health/Performance",
    "Having_-_Resources_-_Means_to_Live/Health/Mental_Health/Performance/Exams",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Hormones",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Organs",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Senses",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Tissue",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Caffeine_before_bed.md",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Go_to_bed_hungry.md",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Intro.md",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Last_full_meal_in_3_hours_before_bed.md",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Munchies_before_bed.md",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Smoking_cigarettes.md",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Time_of_day",
    "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Time_of_day/Sleep_during_night,_awake_during_the_day.md",
)

val timeOfDayItems = listOf(
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Time_of_day/Sleep_during_night,_awake_during_the_day.md",
    ),
)

val sleepDontsItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Caffeine_before_bed.md"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Go_to_bed_hungry.md"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Intro.md"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Last_full_meal_in_3_hours_before_bed.md"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Munchies_before_bed.md"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts/Smoking_cigarettes.md"),
)

val beforeSleepingItems = listOf(
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping/Donts",
        children = sleepDontsItems
    )
)

val sleepItems = listOf(
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Before_Sleeping",
        children = beforeSleepingItems
    ),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep/Time_of_day",
        children = timeOfDayItems,
    ),
)

val bodyItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Hormones"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Organs"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Senses"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body/Tissue"),
)

val physicalHealthItems = listOf(
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Body",
        children = bodyItems,
    ),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health/Sleep",
        children = sleepItems,
    ),
)

val performanceItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Mental_Health/Performance/Exams")
)

val mentalHealthItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Mental_Health/(External)_memory"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Mental_Health/Cognitive_Enhancement"),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Mental_Health/Performance",
        children = performanceItems,
    ),
)

val mindfulnessItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Mindfulness/Burn-out"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Mindfulness/Depression"),
)

val emotionalHealthItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Breathing"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Coping_Mechanisms"),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health/Mindfulness",
        children = mindfulnessItems,
    ),
)

val healthItems = listOf(
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Emotional_Health",
        children = emotionalHealthItems,
    ),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Mental_Health",
        children = mentalHealthItems,
    ),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health/Physical_Health",
        children = physicalHealthItems,
    ),
)

val resourcesItems = listOf(
    TreeItem("Having_-_Resources_-_Means_to_Live/Education"),
    TreeItem("Having_-_Resources_-_Means_to_Live/Enjoyment"),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live/Health",
        children = healthItems,
    ),
)

val presentItems = listOf(
    TreeItem("Being_-_Purpose_-_Meaningful_Life/Present_-_Action/Automation"),
    TreeItem("Being_-_Purpose_-_Meaningful_Life/Present_-_Action/Delegation-outsourcing"),
)

val purposeItems = listOf(
    TreeItem("Being_-_Purpose_-_Meaningful_Life/Future_-_Aspirations"),
    TreeItem("Being_-_Purpose_-_Meaningful_Life/Past_-_Identity"),
    TreeItem(
        path = "Being_-_Purpose_-_Meaningful_Life/Present_-_Action",
        children = presentItems,
    ),
)

val testTreeItems = listOf(
    TreeItem(
        path = "Being_-_Purpose_-_Meaningful_Life",
        children = purposeItems,
    ),
    TreeItem(
        path = "Having_-_Resources_-_Means_to_Live",
        children = resourcesItems,
    ),
)