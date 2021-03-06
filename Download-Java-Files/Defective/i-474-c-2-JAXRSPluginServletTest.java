/*!
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 *
 * Copyright (c) 2002-2019 Hitachi Vantara. All rights reserved.
 *
 */

package org.pentaho.platform.web.servlet;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith( PowerMockRunner.class )
@PrepareForTest( SpringServlet.class )
public class JAXRSPluginServletTest {

  private void validateSendErrorForStatus( JAXRSPluginServlet servlet, int httpStatusCode, boolean shouldCallSendError ) throws ServletException, IOException {
    HttpServletRequest request = mock( HttpServletRequest.class );
    HttpServletResponse response = mock( HttpServletResponse.class );

    when( request.getPathInfo() ).thenReturn( "/url" );
    when( response.getStatus() ).thenReturn( httpStatusCode );

    PowerMockito.suppress( PowerMockito.methodsDeclaredIn( SpringServlet.class ) );

    servlet.service( request, response );

    if ( shouldCallSendError ) {
      verify( response, times( 1 ) ).sendError( httpStatusCode );
    } else {
      verify( response, never() ).sendError( httpStatusCode );
    }
  }

  @Test
  public void service_whenResponseHasHttpErrorStatusCodeThenSendErrorIsCalled_test()
      throws ServletException, IOException {
    JAXRSPluginServlet servlet = new JAXRSPluginServlet();

    for ( int errorStatusCode = 400; errorStatusCode < 600; ++errorStatusCode ) {
      validateSendErrorForStatus( servlet, errorStatusCode, true );
    }
  }

  @Test
  public void service_whenResponseHasHttpNonErrorStatusCodeThenSendErrorIsNotCalled_test()
      throws ServletException, IOException {
    JAXRSPluginServlet servlet = new JAXRSPluginServlet();

    validateSendErrorForStatus( servlet, 200, false );
    validateSendErrorForStatus( servlet, 399, false );
    validateSendErrorForStatus( servlet, 600, false );
  }
}
