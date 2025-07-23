package api.kotlinproject.backend.repo.cassandra

import api.kotlinproject.backend.repo.tests.RepoMlTransformCreateTest
import api.kotlinproject.backend.repo.tests.RepoMlTransformDeleteTest
import api.kotlinproject.backend.repo.tests.RepoMlTransformReadTest
import api.kotlinproject.backend.repo.tests.RepoMlTransformUpdateTest
import api.kotlinproject.repo.common.MlRepoTransformInitialized
import com.benasher44.uuid.uuid4
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.io.File
import java.time.Duration

private fun MlRepoTransformInitialized.clear() {
    (this.repo as RepoMlTransformCassandra).clear()
}

@RunWith(Enclosed::class)
class CassandraTransformTest {

    class RepoMlCassandraCreateTest : RepoMlTransformCreateTest() {
        override val repo = MlRepoTransformInitialized(
            initObjects = initObjects,
            repo = repository(uuidNew.asString())
        )

        /* @AfterTest
         fun tearDown() = repo.clear()*/
    }

    class RepoMlCassandraTransformReadTest : RepoMlTransformReadTest() {
        override val repo = MlRepoTransformInitialized(
            initObjects = initObjects,
            repo = repository()
        )
        /* @AfterTest
         fun tearDown() = repo.clear()*/
    }

    class RepoMlCassandraTransformUpdateTest : RepoMlTransformUpdateTest() {
        override val repo = MlRepoTransformInitialized(
            initObjects = initObjects,
            repo = repository()
        )
        /* @AfterTest
         fun tearDown() = repo.clear()*/
    }

    class RepoMlCassandraTransformDeleteTest : RepoMlTransformDeleteTest() {
        override val repo = MlRepoTransformInitialized(
            initObjects = initObjects,
            repo = repository()
        )
        /* @AfterTest
         fun tearDown() = repo.clear()*/
    }

    /*class RepoMlCassandraSearchTest : RepoMlSearchTest() {
        override val repo = MlRepoInitialized(
            initObjects = initObjects,
            repo = repository()
        )
       /* @AfterTest
        fun tearDown() = repo.clear()*/
    }*/

    @Ignore
    companion object {
        private const val CS_SERVICE = "cassandra"
        private const val CS_PORT = 9042
        private const val MG_SERVICE = "liquibase"

        // val LOGGER = org.slf4j.LoggerFactory.getLogger(ComposeContainer::class.java)
        private val container: ComposeContainer by lazy {
            val resDc = this::class.java.classLoader.getResource("docker-compose-cs.yml")
                ?: throw Exception("No resource found")
            val fileDc = File(resDc.toURI())
            //    val logConsumer = Slf4jLogConsumer(LOGGER)
            ComposeContainer(
                fileDc,
            )
                .withExposedService(CS_SERVICE, CS_PORT)
                .withStartupTimeout(Duration.ofSeconds(300))
                //    .withLogConsumer(MG_SERVICE, logConsumer)
//                .withLogConsumer(PG_SERVICE, logConsumer)
                .waitingFor(
                    MG_SERVICE,
                    Wait.forLogMessage(".*Liquibase command 'update' was executed successfully.*", 1)
                )
        }

        fun repository(uuid: String? = null): RepoMlTransformCassandra {
            return RepoMlTransformCassandra(
                keyspaceName = "mlservice",
                host = container.getServiceHost(CS_SERVICE, CS_PORT),
                port = container.getServicePort(CS_SERVICE, CS_PORT),
                randomUuid = uuid?.let { { uuid } } ?: { uuid4().toString() },
                dc = "dc1",
            )
        }

        @JvmStatic
        @BeforeClass
        fun start() {
            container.start()
        }

        @JvmStatic
        @AfterClass
        fun finish() {
            container.stop()
        }
    }
}
