package api.kotlinproject.app.ktor.repo


/*@Suppress("Since15")
class V1MlRepoCassandraTest : V1MlRepoBaseTest() {
    override val workMode: MlRequestDebugMode = MlRequestDebugMode.TEST
    private fun mkAppSettings(repo: IRepoMl) = MdlAppSettings(
        corSettings = MdlCorSettings(
            repoTest = repo
        )
    )

    override val appSettingsCreate: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(repository(uuidNew))
    )
    override val appSettingsRead: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            repository(),
            initObjects = listOf(initMl),
        )
    )
    override val appSettingsUpdate: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            repository(uuidNew),
            initObjects = listOf(initMl),
        )
    )
    override val appSettingsDelete: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            repository(),
            initObjects = listOf(initMl),
        )
    )
    override val appSettingsSearch: MdlAppSettings = mkAppSettings(
        repo = MlRepoInitialized(
            repository(),
            initObjects = listOf(initMl),
        )
    )

    companion object {
        private const val CS_SERVICE = "cassandra"
        private const val CS_PORT = 9042
        private const val MG_SERVICE = "liquibase"

        // val LOGGER = org.slf4j.LoggerFactory.getLogger(ComposeContainer::class.java)
        private val container: ComposeContainer by lazy {
            val resDc = this::class.java.classLoader.getResource("docker-compose-cs.yml")
                ?: throw Exception("No resource found")
            val fileDc = File(resDc.toURI())
            //  val logConsumer = Slf4jLogConsumer(LOGGER)
            ComposeContainer(
                fileDc,
            )
                .withExposedService(CS_SERVICE, CS_PORT)
                .withStartupTimeout(Duration.ofSeconds(300))
//                .withLogConsumer(MG_SERVICE, logConsumer)
//                .withLogConsumer(PG_SERVICE, logConsumer)
                .waitingFor(
                    MG_SERVICE,
                    Wait.forLogMessage(".*Liquibase command 'update' was executed successfully.*", 1)
                )
        }

        fun repository(uuid: String? = null): RepoMlCassandra {
            return RepoMlCassandra(
                keyspaceName = "mlservice",
                host = container.getServiceHost(CS_SERVICE, CS_PORT),
                port = container.getServicePort(CS_SERVICE, CS_PORT),
                randomUuid = uuid?.let { { uuid } } ?: { uuid4().toString() },
                dc = "dc1",
            ).apply { clear() }
        }

        @JvmStatic
        @BeforeClass
        fun tearUp() {
            container.start()
        }

        @JvmStatic
        @AfterClass
        fun tearDown() {
            container.stop()
        }
    }
}*/
