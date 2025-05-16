package api.kotlinproject.common.exceptions

import api.kotlinproject.common.models.MkplCommand


class UnknownMkplCommand(command: MkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
