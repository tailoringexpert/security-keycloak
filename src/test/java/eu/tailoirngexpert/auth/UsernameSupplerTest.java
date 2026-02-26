/*-
 * #%L
 * TailoringExpert
 * %%
 * Copyright (C) 2025 - 2026 Michael Bädorf and others
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package eu.tailoirngexpert.auth;

import eu.tailoringexpert.auth.UsernameSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class UsernameSupplerTest {

    SecurityContext securityContextMock;
    Authentication authenticationMock;
    UsernameSupplier usernameSupplier;

    @BeforeEach
    void beforeEach() {
        this.securityContextMock = mock(SecurityContext.class);
        this.authenticationMock = mock(Authentication.class);
        this.usernameSupplier = new UsernameSupplier();
    }

    @Test
    void get_NullAuthentication_NullReturned() {
        // arrange
        given(securityContextMock.getAuthentication()).willReturn(null);

        // act
        String actual;
        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContextMock);
            actual = usernameSupplier.get();
        }

        // assert
        assertThat(actual)
            .isNull();
    }

    @Test
    void get_UserNotAuthenticated_NullReturned() {
        // arrange
        given(securityContextMock.getAuthentication()).willReturn(authenticationMock);
        given(authenticationMock.isAuthenticated()).willReturn(FALSE);

        // act
        String actual;
        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContextMock);

            actual = usernameSupplier.get();
        }

        // assert
        assertThat(actual)
            .isNull();
    }

    @Test
    void get_UserAuthenticated_NameReturned() {
        // arrange
        given(securityContextMock.getAuthentication()).willReturn(authenticationMock);
        given(authenticationMock.isAuthenticated()).willReturn(TRUE);
        given(authenticationMock.getName()).willReturn("testuser");

        // act
        String actual;
        try (MockedStatic<SecurityContextHolder> securityContextHolderMock = mockStatic(SecurityContextHolder.class)) {
            securityContextHolderMock.when(SecurityContextHolder::getContext).thenReturn(securityContextMock);

            actual = usernameSupplier.get();
        }

        // assert
        assertThat(actual)
            .isNotNull()
            .isEqualTo("testuser");
    }
}
