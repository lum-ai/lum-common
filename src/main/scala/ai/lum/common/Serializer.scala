/*
 * Copyright 2016 lum.ai
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

package ai.lum.common

import java.io._
import org.apache.commons.io.input.ClassLoaderObjectInputStream
import TryWithResources.using

object Serializer {

  /** serialize object to output stream */
  def serialize[A](obj: A, outputStream: OutputStream): Unit = {
    using(new ObjectOutputStream(outputStream)) { oos =>
      oos.writeObject(obj)
    }
  }

  /** serialize object to file */
  def serialize[A](obj: A, file: File): Unit = {
    using(new FileOutputStream(file)) { fos =>
      serialize(obj, fos)
    }
  }

  /** serialize object to file */
  def serialize[A](obj: A, filename: String): Unit = {
    using(new FileOutputStream(filename)) { fos =>
      serialize(obj, fos)
    }
  }

  /** serialize object to byte array */
  def serialize[A](obj: A): Array[Byte] = {
    using(new ByteArrayOutputStream()) { baos =>
      serialize(obj, baos)
      baos.toByteArray
    }
  }

  /** deserialize from input stream */
  def deserialize[A](inputStream: InputStream): A = {
    deserialize[A](inputStream, getClass().getClassLoader())
  }

  /** deserialize from input stream */
  def deserialize[A](inputStream: InputStream, classLoader: ClassLoader): A = {
    using(new ClassLoaderObjectInputStream(classLoader, inputStream)) { ois =>
      ois.readObject().asInstanceOf[A]
    }
  }

  /** deserialize from file */
  def deserialize[A](file: File): A = {
    deserialize[A](file, getClass().getClassLoader())
  }

  /** deserialize from file */
  def deserialize[A](file: File, classLoader: ClassLoader): A = {
    using(new FileInputStream(file)) { fis =>
      deserialize[A](fis, classLoader)
    }
  }

  /** deserialize from file */
  def deserialize[A](filename: String): A = {
    deserialize[A](filename, getClass().getClassLoader())
  }

  /** deserialize from file */
  def deserialize[A](filename: String, classLoader: ClassLoader): A = {
    using(new FileInputStream(filename)) { fis =>
      deserialize[A](fis, classLoader)
    }
  }

  /** deserialize from byte array */
  def deserialize[A](bytes: Array[Byte]): A = {
    deserialize[A](bytes, getClass().getClassLoader())
  }

  /** deserialize from byte array */
  def deserialize[A](bytes: Array[Byte], classLoader: ClassLoader): A = {
    using(new ByteArrayInputStream(bytes)) { bais =>
      deserialize[A](bais, classLoader)
    }
  }

}
