/*
 * Sonar Groovy Plugin
 * Copyright (C) 2010-2019 SonarSource SA & Community
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.groovy.surefire.api;

import java.io.File;
import java.util.Optional;
import javax.annotation.CheckForNull;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Configuration;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public final class SurefireUtils {

  private static final Logger LOGGER = Loggers.get(SurefireUtils.class);
  public static final String SUREFIRE_REPORTS_PATH_PROPERTY = "sonar.junit.reportsPath";

  private SurefireUtils() {
  }

  public static File getReportsDirectory(Configuration settings, FileSystem fs, PathResolver pathResolver) {
    File dir = getReportsDirectoryFromProperty(settings, fs, pathResolver);
    if (dir == null) {
      dir = new File(fs.baseDir(), "target/surefire-reports");
    }
    return dir;
  }

  @CheckForNull
  private static File getReportsDirectoryFromProperty(Configuration settings, FileSystem fs, PathResolver pathResolver) {
    Optional<String> path = settings.get(SUREFIRE_REPORTS_PATH_PROPERTY);
    if (path.isPresent()) {
      try {
        return pathResolver.relativeFile(fs.baseDir(), path.get());
      } catch (Exception e) {
        LOGGER.info("Surefire report path: " + fs.baseDir() + "/" + path + " not found.", e);
      }
    }
    return null;
  }

}
