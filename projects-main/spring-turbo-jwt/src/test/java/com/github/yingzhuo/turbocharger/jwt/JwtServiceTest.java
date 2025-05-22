/*
 *
 * Copyright 2022-present the original author or authors.
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
 *
 */
package com.github.yingzhuo.turbocharger.jwt;

import com.github.yingzhuo.turbocharger.jwt.algorithm.RSA512AlgorithmFactoryBean;
import org.junit.jupiter.api.Test;

public class JwtServiceTest {

	@Test
	void test() {
		var factoryBean = new RSA512AlgorithmFactoryBean();
		factoryBean.setCertificatePemContent(
			"""
				-----BEGIN CERTIFICATE-----
				MIICsjCCAZqgAwIBAgIUTdMkQsog9u9UHGvFaNgBlvnph4EwDQYJKoZIhvcNAQEL
				BQAwEzERMA8GA1UEAwwIeWluZ3podW8wHhcNMjUwNTIyMjEzMjMzWhcNMjYxMjMx
				MTU1OTU5WjATMREwDwYDVQQDDAh5aW5nemh1bzCCASIwDQYJKoZIhvcNAQEBBQAD
				ggEPADCCAQoCggEBANYQyLr37pannQjpjH/N36+5vn48lVTfRPtgFpYYi02bicwI
				62KezrcZSkXFASFKtKt3s6iQsTn7x8GzmSWjHO9u71YcxJTifReYZOySgARl70KT
				KE+6C5yDik45F+dbzIHPc4M/wL/CMMao7GF5LlshQ/e9jHzbpPrBTwalDyZhe26c
				5JMaoo1bils6naipMJAcSV/EtkfINLhg4P1IW96bydIpf2JuQECX22oe2r5KKk6y
				J2Ztp356NiQB/JOii4N/imTlSdZn3fq9YxNQdig9V3x09WEM0jNDTlmDgBnNXrgt
				0Pg8xdsIsStuQFJd1/R2yl9E13lh6HLm3YmSZv0CAwEAATANBgkqhkiG9w0BAQsF
				AAOCAQEAuM6eQgzm/ZJ5rutSo3VpH4D0fHEfT+j7dh430KnNYAEIbTwlunL+hM0w
				er8pD66bFW01OmJSfzEeeL+ms0gANgTCKQfNmjmb/y0sFFFuyqFkKm9INOr7O5SF
				ECHSe4TU+UljKSC1JV7vlDu4GgAVXB0A7Cl6Ip2yLJuHs0Ee3zQk6komoOXuJP24
				t6jchKRnzTFbxOa3rT8UfbBbU1Y7IzDfQ3spkDMoaHQH3YziZ0aoQlbqvLd2FfKB
				YymQkWnk+/RYWriludTHTjsvORw4EPts2HjoITrSF7UihWyVDUXYokCm6G7tr3PM
				Xx6YU7RxA84R1sgdD0YtdhKuuGcoWw==
				-----END CERTIFICATE-----
				"""
		);
		factoryBean.setPrivateKeyPemContent(
			"""
				-----BEGIN PRIVATE KEY-----
				MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDWEMi69+6Wp50I
				6Yx/zd+vub5+PJVU30T7YBaWGItNm4nMCOtins63GUpFxQEhSrSrd7OokLE5+8fB
				s5kloxzvbu9WHMSU4n0XmGTskoAEZe9CkyhPugucg4pOORfnW8yBz3ODP8C/wjDG
				qOxheS5bIUP3vYx826T6wU8GpQ8mYXtunOSTGqKNW4pbOp2oqTCQHElfxLZHyDS4
				YOD9SFvem8nSKX9ibkBAl9tqHtq+SipOsidmbad+ejYkAfyToouDf4pk5UnWZ936
				vWMTUHYoPVd8dPVhDNIzQ05Zg4AZzV64LdD4PMXbCLErbkBSXdf0dspfRNd5Yehy
				5t2Jkmb9AgMBAAECggEAK186NHuwiTDLxfcAAxMU69dQC+a9gPU91krJOL8fVmmZ
				uU/jVVeE3Z9Pp6Q7TuICii1WJuSuK+fiONJpSLFsVuzcrE9m2x2qp8G2TSJ/sqRu
				nkgPplu1J5CVULULrVIxtOlx55letdukI1QtlLBHXqp2SXdLxC6CllXda6S81ZMS
				rJXzu5Nr+JfTK7Do8Qux84qWXY0rJJrr2nWL5oiN0ww95vRvY48EXzimYGQd49ys
				jWUIZ3l/IKYAmQB/2GjcbSjjKI+UWPeHCr7j5icZE/r6TtsfxLxm7SzgoBeU1zCB
				TpyWuF8IddxGa0783YQf5eN/wqSyMYmabTUJ3EITAQKBgQD5/8C81rFaX0a9et2u
				Y4ogch01408qcfaybwO/usUE3WaE9YUAXM9MyNpaoz2JZ00h6H+eTDisx16/+dfa
				5bgDOvMFmL0ibj6xKFuITQ+V59hDIzku1FAKeDZIsMno3kfTewy4tI73jgJ33qCC
				w+psY4NzG3qEhEdOxuKgqIDAfQKBgQDbNDg3oZc+LHwpPkBojBX0HAT/JNMTcMFQ
				rHOcvfp9FFAFHHNTzrwN8Xgf6EWhz9vQ9dYJlFLokhaqep17g1CIV0DBMNuMqSnS
				Hfy92FPrYkIBQXG0/TCxmy+c7MlX3axOErAcnNTDTdlJvl6/CncOVNIIdT4W9K8F
				xkkGZluIgQKBgFCfQPnlRta9KSQMDpehtDbYdkEdqDco/4T1tkDLq6Pw/pHcV+MR
				Cy1ZDMn0IPFB5icMQrO7Iv/dX9oLaahHMvuftCXW5w/Ge+NH/5TKPT97wEuOfW9z
				ushVkjQJB2RY4tL5uNWMLRr6qiMcIalqIxyHTyn875ryychG7WkgTj1FAoGAHown
				bXFCb3KpluHK/gbPpGYnOBs8ow/qkMCHmAdNmhKzmBK/66T6xgKpl+C5m3QpEPTB
				cDJQX4LET9uoiKbjGSz3pDSX1AFbB6IQL7Cu+TWHNz+UhgFmwMl8mCTTZkBJG9Q0
				4LfmWpVFu6Yg9ASUVNXzMySLk41juq1ITfEDvoECgYEAlocjcn990pxHnkd5p4fD
				vNmU+EWoin1tO4Oke8qvvdp6+JOlzNhIcjUK3LFnFouXml+8uBg4b/9vBWch3QR8
				YVP+whjkG4dSGMALs24Bw4tdqPNBkzw8ZRNgHxCpaqaqTc+AhKf0kVB7GhzGnOjH
				lWkH8tTpxTyIn96oKHzIFaY=
				-----END PRIVATE KEY-----
				"""
		);
		factoryBean.setPrivateKeyPassword(null);

		var alg = factoryBean.getObject();
		System.out.println(alg);
	}

}
