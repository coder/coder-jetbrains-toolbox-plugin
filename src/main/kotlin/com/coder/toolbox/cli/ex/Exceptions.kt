package com.coder.toolbox.cli.ex

class ResponseException(message: String, val code: Int) : Exception(message)

class SSHConfigFormatException(message: String) : Exception(message)

class MissingVersionException(message: String) : Exception(message)
