/*
 * Copyright 2016 Edward Samson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.samson.duck

import java.io.{File, FileNotFoundException}

import com.typesafe.scalalogging.StrictLogging
import scopt.OptionParser

import scala.util.Try

object Main extends StrictLogging {

  case class Config(files: Seq[File] = Seq())

  object ExitCode {
    val Success = 0
    val BadArgs = 1
    val Error = -1
  }

  def main(args: Array[String]) {
    val parser = new OptionParser[Config]("duck") {
      head("Duck")

      arg[File]("<file>...") unbounded() action { (file, config) =>
        config.copy(files = config.files :+ file)
      } text ("The files")
    }

    sys.exit(
      parser.parse(args, Config()) map {
        config => {
          Try {
            run(config)
            ExitCode.Success
          } recover {
            case ex =>
              logger.error(s"duck failed", ex)
              ExitCode.Error
          } get
        }
      } getOrElse ExitCode.BadArgs
    )
  }

  def run(config: Config): Unit = {
    logger.info(s"Duck! $config")

    config.files.foreach { file =>
      if (!file.canRead) {
        throw new FileNotFoundException(s"$file")
      }
    }
  }
}
