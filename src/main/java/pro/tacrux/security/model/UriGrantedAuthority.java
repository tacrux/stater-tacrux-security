/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pro.tacrux.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.tacrux.security.util.JsonUtil;

import java.util.Objects;

/**
 * Basic concrete implementation of a {@link GrantedAuthority}.
 *
 * <p>
 * Stores a {@code String} representation of an authority granted to the
 * {@link org.springframework.security.core.Authentication Authentication} object.
 *
 * @author Luke Taylor
 */
@Data
public final class UriGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final RequestMethod method;
	private final String uri;
	private final String systemCode;

	public UriGrantedAuthority(RequestMethod method, String uri, String systemCode) {
		this.method = method;
		this.uri = uri;
		this.systemCode = systemCode;
	}


	@Override
	public String getAuthority() {
		return JsonUtil.to(this);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof UriGrantedAuthority) {
			return (this.method.equals(((UriGrantedAuthority) obj).method)
					&& (this.uri.equals(((UriGrantedAuthority) obj).uri))
			&&(this.systemCode.equals(((UriGrantedAuthority) obj).systemCode)));
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(method, uri, systemCode);
	}

	@Override
	public String toString() {
		return this.getAuthority();
	}


}
