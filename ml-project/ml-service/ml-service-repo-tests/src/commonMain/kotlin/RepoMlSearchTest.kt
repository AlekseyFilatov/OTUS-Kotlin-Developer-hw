package api.kotlinproject.backend.repo.tests

import api.kotlinproject.common.models.MdlMl
import api.kotlinproject.common.models.MdlUserId
import api.kotlinproject.common.repo.IRepoMl


abstract class RepoMlSearchTest {
    abstract val repo: IRepoMl

    protected open val initializedObjects: List<MdlMl> = initObjects

   /* @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchMl(DbMlFilterRequest(ownerId = searchOwnerId))
        assertIs<DbMlsResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }*/

    companion object: BaseInitMls("search") {

        val searchOwnerId = MdlUserId("owner-124")
        override val initObjects: List<MdlMl> = listOf(
            createInitTestModel("ml1"),
            /*createInitTestModel("ml2", ownerId = searchOwnerId),
            createInitTestModel("ml3", mlType = MdlDealSide.SUPPLY),
            createInitTestModel("ml4", ownerId = searchOwnerId),
            createInitTestModel("ml5", mlType = MdlDealSide.SUPPLY),*/
        )
    }
}
