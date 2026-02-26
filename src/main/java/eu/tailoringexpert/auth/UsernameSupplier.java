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
package eu.tailoringexpert.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.function.Supplier;

import static java.util.Objects.nonNull;

/**
 * Interfacr for getting Login username.
 *
 * @author Michael Bädorf
 */
public class UsernameSupplier implements Supplier<String> {
    @Override
    public String get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (nonNull(authentication) && authentication.isAuthenticated()) ?
            ((Jwt) authentication.getPrincipal()).getClaimAsString("preferred_username"):
            null;
    }
}
