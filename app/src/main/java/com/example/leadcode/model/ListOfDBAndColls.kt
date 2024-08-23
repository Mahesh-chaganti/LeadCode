package com.example.leadcode.model

data class ListOfDBCollsDocs(
    val listOfDbColls : List<DBAndColl> = listOf(),
    val articleDocs: List<CollsAndDocs> = listOf(),
    val tutorialDocs: List<CollsAndDocs> = listOf()
)

data class DBAndColl(
   val database: String? = null,
   val collections: List<String> = listOf()

)
data class CollsAndDocs(
    val collection: String? = null,
    val docs: List<String> = listOf()
)
data class CombinedItem(
    val database: String? = null,
    val collection: String? = null,
    val doc: String? = null
)

fun combineAndShuffle(
    dbColls: List<DBAndColl>,
    articleDocs: List<CollsAndDocs>,
    tutorialDocs: List<CollsAndDocs>
): List<CombinedItem> {
    val combinedList = mutableListOf<CombinedItem>()

    // Add docs from articleDocs
    articleDocs.forEach { collsAndDocs ->
        collsAndDocs.docs.forEach { doc ->
            combinedList.add(CombinedItem(database = "ArticlesDb", collection = collsAndDocs.collection,doc = doc ))
        }
    }

    // Add docs from tutorialDocs
    tutorialDocs.forEach { collsAndDocs ->
        collsAndDocs.docs.forEach { doc ->
            combinedList.add(CombinedItem(database = "TutorialsDb", collection = collsAndDocs.collection,doc = doc ))
        }
    }

    // Add collections from listOfDbColls
    dbColls.forEach { dbcolls ->
        dbcolls.collections.forEach { collection ->
            combinedList.add(CombinedItem(database =  dbcolls.database, collection = collection,doc = null))
        }
    }

    return combinedList.shuffled()
}