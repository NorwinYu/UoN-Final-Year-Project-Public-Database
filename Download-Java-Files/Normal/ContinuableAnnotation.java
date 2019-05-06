/**
 * ﻿Copyright 2013-2019 Valery Silaev (http://vsilaev.com)
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
package org.apache.commons.javaflow.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Meta-annotation that is used to annotate other continuation-related
 * annotations. It provides an option to declare and use own annotations instead
 * of supplied {@link continuable} and {@link ccs} annotations, for ex:
 * 
 * <pre>
 * <code>
 * import java.lang.annotation.Documented;
 * import java.lang.annotation.ElementType;
 * import java.lang.annotation.Retention;
 * import java.lang.annotation.RetentionPolicy;
 * import java.lang.annotation.Target;
 * 
 * {@literal @}Documented
 * {@literal @}Retention(RetentionPolicy.CLASS)
 * {@literal @}Target({ElementType.METHOD})
 * <b>{@literal @}ContinuableAnnotation</b>
 * public {@literal @}interface ContinuableMethod {
 *   // The annotation to mark continuable methods
 * }
 * 
 * </code>
 * </pre>
 * 
 * @author Valery Silaev
 *
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ContinuableAnnotation {

}