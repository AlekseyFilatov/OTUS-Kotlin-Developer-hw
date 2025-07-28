import api.kotlinproject.backend.repo.tests.*
import api.kotlinproject.repo.common.MlRepoInitialized
import api.kotlinproject.repo.inmemory.MlRepoInMemory

class MlRepoInMemoryCreateTest : RepoMlCreateTest() {
    override val repo = MlRepoInitialized(
        MlRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class MlRepoInMemoryDeleteTest : RepoMlDeleteTest() {
    override val repo = MlRepoInitialized(
        MlRepoInMemory(),
        initObjects = initObjects,
    )
}

class MlRepoInMemoryReadTest : RepoMlReadTest() {
    override val repo = MlRepoInitialized(
        MlRepoInMemory(),
        initObjects = initObjects,
    )
}

class MlRepoInMemorySearchTest : RepoMlSearchTest() {
    override val repo = MlRepoInitialized(
        MlRepoInMemory(),
        initObjects = initObjects,
    )
}

class MlRepoInMemoryUpdateTest : RepoMlUpdateTest() {
    override val repo = MlRepoInitialized(
        MlRepoInMemory(),
        initObjects = initObjects,
    )
}
