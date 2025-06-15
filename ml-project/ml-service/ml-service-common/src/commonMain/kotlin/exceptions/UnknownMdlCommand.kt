package api.kotlinproject.common.exceptions

import api.kotlinproject.common.models.MdlCommand


class UnknownMdlCommand(command: MdlCommand) : Throwable("Wrong command $command at mapping toTransport stage")
