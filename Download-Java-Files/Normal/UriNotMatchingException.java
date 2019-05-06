/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package com.pogeyan.cmis.api.uri;

import com.pogeyan.cmis.api.uri.exception.BaseUriException;
import com.pogeyan.cmis.api.uri.exception.MessageReference;

/**
 * URI-parsing exception resulting in a 404 Not Found response.
 * 
 */
public class UriNotMatchingException extends BaseUriException {

  private static final long serialVersionUID = 1L;

  public static final MessageReference MATCHPROBLEM = createMessageReference(UriNotMatchingException.class,
      "MATCHPROBLEM");
  public static final MessageReference NOTFOUND = createMessageReference(UriNotMatchingException.class, "NOTFOUND");
  public static final MessageReference CONTAINERNOTFOUND = createMessageReference(UriNotMatchingException.class,
      "CONTAINERNOTFOUND");
  public static final MessageReference ENTITYNOTFOUND = createMessageReference(UriNotMatchingException.class,
      "ENTITYNOTFOUND");
  public static final MessageReference PROPERTYNOTFOUND = createMessageReference(UriNotMatchingException.class,
      "PROPERTYNOTFOUND");

  public UriNotMatchingException(final MessageReference messageReference) {
    super(messageReference);
  }

  public UriNotMatchingException(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause);
  }

  public UriNotMatchingException(final MessageReference messageReference, final String errorCode) {
    super(messageReference, errorCode);
  }

  public UriNotMatchingException(final MessageReference messageReference, final Throwable cause,
      final String errorCode) {
    super(messageReference, cause, errorCode);
  }
}
