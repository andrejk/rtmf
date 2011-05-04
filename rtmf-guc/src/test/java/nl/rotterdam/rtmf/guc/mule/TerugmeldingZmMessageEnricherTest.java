/*
 * Copyright (c) 2009-2011 Gemeente Rotterdam
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the European Union Public Licence (EUPL),
 * version 1.1 (or any later version).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * European Union Public Licence for more details.
 *
 * You should have received a copy of the European Union Public Licence
 * along with this program. If not, see
 * http://www.osor.eu/eupl/european-union-public-licence-eupl-v.1.1
*/
package nl.rotterdam.rtmf.guc.mule;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

import nl.rotterdam.rtmf.guc.StelselCatalogusCache;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.FunctionalTestCase;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class TerugmeldingZmMessageEnricherTest extends FunctionalTestCase {
	private Wiser smtpServer;

	String zendTerugmeldingGM = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">Component-Test-UUID</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>RDAM-02</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>GM-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
			+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>GM-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Peter</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:contactInfo>"
			+ "				<ns2:naam></ns2:naam>"
			+ "				<ns2:telefoon></ns2:telefoon>"
			+ "				<ns2:email></ns2:email>"
			+ "			</ns2:contactInfo>"
			+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";

	String emailTerugmeldingZM = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+ "	<S:Header>"
			+ "		<To xmlns=\"http://www.w3.org/2005/08/addressing\">http://localhost:63081/tmfTerugmeldService</To>"
			+ "		<Action xmlns=\"http://www.w3.org/2005/08/addressing\">terugmeldingtmf-aanmelden-00000003271987420000</Action>"
			+ "		<ReplyTo xmlns=\"http://www.w3.org/2005/08/addressing\">"
			+ "			<Address>http://www.w3.org/2005/08/addressing/anonymous</Address>"
			+ "		</ReplyTo>"
			+ "		<MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">Component-Test-UUID</MessageID>"
			+ "	</S:Header>"
			+ "	<S:Body>"
			+ "		<ns2:terugmelding"
			+ "		xmlns:ns2=\"http://wus.tmf.gbo.overheid.nl/wsdl/aanmeldService-V1.1.xsd\""
			+ "		xmlns:ns3=\"http://tmfportal.ovsoftware.com/services\" xmlns:ns4=\"http://tmfportal.ovsoftware.com/services/defaultreply.xsd\">"
			+ "			<ns2:meldingKenmerk>UnitTester-001</ns2:meldingKenmerk>"
			+ "			<ns2:tijdstempelAanlevering>2009-08-27T15:51:58.635+02:00</ns2:tijdstempelAanlevering>"
			+ "			<ns2:basisRegistratie>RDAM-01</ns2:basisRegistratie>"
			+ "			<ns2:objectTag>GM-PERSOON</ns2:objectTag>"
			+ "			<ns2:objectIdentificatie>adres</ns2:objectIdentificatie>"
			+ "			<ns2:toelichting>Unit tester</ns2:toelichting>"
			+ "			<ns2:attributen>"
			+ "				<ns2:attribuutIdentificatie>GM-PERSOON-VOORNAAM</ns2:attribuutIdentificatie>"
			+ "				<ns2:betwijfeldeWaarde>Petet</ns2:betwijfeldeWaarde>"
			+ "				<ns2:voorstel>Peter</ns2:voorstel>"
			+ "			</ns2:attributen>"
			+ "			<ns2:contactInfo>"
			+ "				<ns2:naam>De Tester</ns2:naam>"
			+ "				<ns2:telefoon>555-1982</ns2:telefoon>"
			+ "				<ns2:email>de.tester@de.test.fabriek.nl</ns2:email>"
			+ "			</ns2:contactInfo>"
			+ "			<ns2:attachment>"
			+ "				<ns2:filename>test.pdf</ns2:filename>"
			+ "				<ns2:base64attachment>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Y3Rpb25bMSAwIFIgL1hZWiBudWxsIG51bGwgMF0KL0xhbmcobmwtTkwpCj4+CmVuZG9iagoKMTMgMCBvYmoKPDwvQ3JlYXRvcjxGRUZGMDA1NzAwNzIwMDY5MDA3NDAwNjUwMDcyPgovUHJvZHVjZXI8RkVGRjAwNEYwMDcwMDA2NTAwNkUwMDRGMDA2NjAwNjYwMDY5MDA2MzAwNjUwMDJFMDA2RjAwNzIwMDY3MDAyMDAwMzMwMDJFMDAzMD4KL0NyZWF0aW9uRGF0ZShEOjIwMDkwOTI5MTcwMjQ0KzAyJzAwJyk+PgplbmRvYmoKCnhyZWYKMCAxNAowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDc4MTUgMDAwMDAgbiAKMDAwMDAwMDAxOSAwMDAwMCBuIAowMDAwMDAwMjEyIDAwMDAwIG4gCjAwMDAwMDc5NTggMDAwMDAgbiAKMDAwMDAwMDIzMiAwMDAwMCBuIAowMDAwMDA2OTg2IDAwMDAwIG4gCjAwMDAwMDcwMDcgMDAwMDAgbiAKMDAwMDAwNzIwMSAwMDAwMCBuIAowMDAwMDA3NTM0IDAwMDAwIG4gCjAwMDAwMDc3MjggMDAwMDAgbiAKMDAwMDAwNzc2MCAwMDAwMCBuIAowMDAwMDA4MDU3IDAwMDAwIG4gCjAwMDAwMDgxNTQgMDAwMDAgbiAKdHJhaWxlcgo8PC9TaXplIDE0L1Jvb3QgMTIgMCBSCi9JbmZvIDEzIDAgUgovSUQgWyA8OUQ1QThEMEM4MjUyOEE4QUU1MEExQTMxNjdBMDcwOTg+Cjw5RDVBOEQwQzgyNTI4QThBRTUwQTFBMzE2N0EwNzA5OD4gXQovRG9jQ2hlY2tzdW0gL0UzQzg2NDU4NUFGRDQzMTM5NzA4QTlCRkQ4RUQxOEVFCj4+CnN0YXJ0eHJlZgo4MzQxCiUlRU9GCg=="
			+ "				</ns2:base64attachment>"
			+ "			</ns2:attachment>"
			+ "			<ns2:attachment>"
			+ "				<ns2:filename>test2.pdf</ns2:filename>"
			+ "				<ns2:base64attachment>JVBERi0xLjQKJcOkw7zDtsOfCjIgMCBvYmoKPDwvTGVuZ3RoIDMgMCBSL0ZpbHRlci9GbGF0ZURlY29kZT4+CnN0cmVhbQp4nDPQM1Qo5ypUMFDQNQAyzQyNgKS5JYgsSlUI11LIA0uCYFE6l1MIl6mZnoWCuZGZQkiKgr6boYKhgUJIWrSNgaGdEZQwsosN8eJyDeEKxKrV0ETPGF2zMUifCZwwxW+CgZGeOboJZiB95iDCAkRYIkwIVAAA1ZwxRgplbmRzdHJlYW0KZW5kb2JqCgozIDAgb2JqCjEyMgplbmRvYmoKCjUgMCBvYmoKPDwvTGVuZ3RoIDYgMCBSL0ZpbHRlci9GbGF0ZURlY29kZS9MZW5ndGgxIDEzMjU2Pj4Kc3RyZWFtCnic5VoLeFTVtd5n1pxJMnnNhCQEQsJJYnjo5EEQEUSTSTIJA3k5MzytymTmJBnIPJwHAbnUWC5YJTRaNIrlKqLFZy3XcjEYTCliq1Wv9YFtsa3S0lpsSq0F7LVhc9fe58wjEdGvvffr992bIefsx9pr/etfa6+9Jx/hYEQmaaSXAKlxeZ2B4vTUZELIq4QIWa61YSk9aeBhbL9PiKayI9DpHfGTZkK0BpR5qrN7fcdDNPxn7B8kRFfZJTvdH2R5ZhCiH8H5K7pwYCr1JRGSegn2L+nyhtfdqy3RYn8h9nO7/S5nsW4fm1/F+l7nusCJpFtF7K/DvuRzeuX3trxTh/37sHs64A+FV5JbzxNi6mPzgaAc2Dz8oyPYf4qQpHwcE/DDftKwqWN9DWhFXVJyCvn/+jNIXuG/Twh34buDj9ymuYVoYp9B8gLOa7jcoPCKcLvwHLb3kFF8biIfC3r4kTAXW8O4drm2CEf7yU6+sh8+IBE4SN4iL5N3sfWBMA9wrfAWKRLeQ223x63AMPZewOcGGIblwlTBSx4RnkaNG9Cmn9yiwbfGhppf076Bo6+R2/CznTxC/NhmyDYh/l+SfWQrOU3u05wkK7H9HHkR8VCSodgQjpKzqOkJzdWaDpR7EbXdT+4XNpGjJKQlgh4lj4tHNZeh1n3oASHtZKd4VLyP8YHvo+JHOENIgW5Ql51Ugl4w3vYIB4VZmhbyFq7fQBzwFbgJ3hU2Yxr3wEnSryGwiqwmr4tHddmkP6mE9Os6hPXaVfyzgfmn6dGuEp4gJ1FnO/wV+0WIbCf3mJB9GpvYIragzx04tpM/+5WnzkBeg0+R97s0VFiobYBqnNmgbSL3kd24cjoyQ4gf5qB1P9kgblM+5An8lInbYAD1czaE2ZqryU5Nh7AV0Z5FNv1QT+aijQLxFNks7EPcJGkjCYlHCckmzybpRC1oBGKSDHs1pVb33pprl0svrSgqM43rSoYkaS9p25u+Xho8f75tuTZfXLFXnLIXSpP3aktLjn/e5PEy0+K25dKgMNFSr6q1rKrHQftybLIeDuO4pZ7PMat7xVL8Z121V3J1SXcY7iiZf4dBnl+GMe+iA9ou8RGsXklk6kGiFdAZohOy9wvJ4iaNllQceXtkFjG8PfL2SOUEY5GxtMhY1KUloyHIH/0tHUjK+OvHQd1Mtk80ZPP532BUN5BUMpP4ai7VJ5HJUlpeZhLZn5fUl1W0Vfp+Qd8lw1k78tKFPO2kDL0urV7S6nKuudRweuTI6EhVlXHevFlo8cTp0dMjhlOGU8Z5xnlZ84xZ8yprCiqnVkqVRZXFu8guYZdml35X6u7cXRN35e2atGtyxvVCSbEuJzt3dtUVc+fMNZbMycCBaXMuv+IqYU45m0uac40wuyp3YqGgGb76oYdv7r7vaeHAgau+1/vkq3/7yyfClu03HLquY2j51hevniZpZt8UkANvPTez6dyte9w3/mD30OGCLeuvuHxw+nSbrWq74uswXap1oK8GMoXMrpkikuzJfRnZfcnDGTuEF+BwgTErtXGilug0DQXoXVXVKGPxxIkTzDF0s7LUWGKcncNAZRkNGgQrXD4t5oPQc+BA+Q73Kx/8/scdO4SPbtu85Y47tmy+bdu5l3X6fvsy+kP6If0TfXmZcOadY+8effsXxxgmAasQ0eYjpgnEVJOXkgEEBF2fcTjtsF7Q6Mii9BRdakM2Q8PAVCxALMaJCEVAKIrxGKZcbf6B1TfdunVwcNZjoScf1+w/t0iz/95vPPvkudu0qx5b5TrOObgd472Qx3siKamZoOvLIn1pLL4pWZn1kJVzTR4aUxxnblcKUf/mXD5tuuq3xmjI0nT13Xln37Y779x28pOzH548exbeO/bO0XffPfrOsZ30Tfprepy+JZRhySsUypmfm9DPZPEB9HNWTV6GmJwJ+4lROJy8X5+cmoI5qzNkZWQb3l5wZHTBkSqeUiOnFxwZYemFzhblFBmzc68ScliGGIvmFBlxa++mN9wQef3460/Qo8Jl4gP0cP+5Xf/Svn3PK5pV/cI1aNOLNv3oazF5uGZ6XlZmijaJFOTrknLS+iQYzj88yZBEjJnJzboWY3Nmy5S85smWEsPpxXvTHIv3Gh3XLT9AJp8/dOWK0QWjyAhmNAsARmABQ4W4MBCVNY2V2kqxUleZVJlcmVKpr0ytzq2eWJ1XPal6cnV+9ZTqgurCXujV9oq9ut6k3uTelF59b2p/bv/E/rz+Sf2T+/P7p/QX9BeWCNcLPIiThITAjo+w5pFiz6Lb/I/NaWi76rF5i6zzHn20yFXdJMOphZY36HvnejS3/jG04bfnbtHc+lGAvbWrVi2obmBxfwa5qMeql0LSSXnNpOT9JO2I/lvksE6zXwtNqYJObCKL9UkZGIITI6MLWLKNVJ0Y5SlQxPhHzouE2TkleNn4qeAd9QleelyYOjioXTVa0d8PtZq6k8zOBsyv5ch5Du4xS800kguCfmvKNl3ufszrdOEHk/omDKfvKADNFENKro5Yp2QZFhbwlDuCDM9T8u4EKySG06ewjmD0c4rUKpGDFUIiRgPBDZiklAjt8tHffP/p5Ye8nheuo3+jxwTpo3c+GdTe9fXNTxk0N6zUPfvSlfOevewyYZ4wQUgTauivXtz9+N6dmBtHEacOcU4nkZqa9DRNRurEqYXJKZok/cTCqYW1BYV5+tTCqdocslU4pM3emnMor8+o7SsdNu6YUaBPnZqfRFrzdRnWJF12sWWG4fQRdOAEcyDqgoGeOWU4cyprIi+FWOaTDBl/xIxJ4s8VxUJOuVr/2BbJydZhZk+fUyjwnVahKRewEGLE4bXWXfaNG77y7KLbt428ad+/uvOg4+YtZ5ItD37z2I9X7tHO21defq198aKSjMk7N+4ZKikZnjPHtaJ3liZj6vZbHvpuEa8xGJfGp9Ph1ZQbMxecIVOT+RXpxZe+9pP4hQkro5+diSQ5NoTrkry0IOFWJYy7ZQl4gneRzXhHegHvOZtwvz2Dt4WjfG4anua/Q89WCndrJmtaNLshT9WgJ5dinigV2UB2MIviMrzHigTvyCRXyIjZuTFmU0DJG9W2BuX8ahtwPKS2tdjepLZFvAtvV9s6vCk9qLaTiZF8R22nkgJErrTTU7bjXU5pZ5DL9T7ULGjZ/XlYv1ttC0TCG7vS1pDk1F61DTj+dbWtxfZjalskeakvqW0dmZL6vtpOJsWpo2o7lcxPK1Tb6ROmpS1X2xmka+pX6/yB9UFPZ1dYmuGaKVVVVs6W2tdLtZ5wKByUnV6TZPW5yiVzd7dkY1IhySaH5OBa2V2ur5dXO5dG8OLg9HXKIckZlCWPTwpE2rs9Lsnt9zo9vqiM3ekLSc1+n7/W718zfmx8f6kcDHn8PqmqvOpKZY5NJUh2+H0IJIzwusLhwPyKCjeOr42Uh/yRoEvu8Ac75XKfHG7gYgwWcy3mkjQjJMtSu9zt75lZLn0JJ8r1+vhiBOeUFM0x6vRlF/3R6/9+kqVxlj0IUQoHnW7Z6wyukfwd47Xo9W1y0OsJcQZRuksOymirM+j0hWW3SeoIovO4DB1GmkxS2C85feulAHKOC/ztYXTY4+tEKy4EzSTDXbLKuNPl8nsDKM4Ewl2oHUmSfSEkuJhTUjwTlbklZyjkd3mcaA8ZdEW8si/sDDM8HZ5u5HgG08gXSHZ/R7gHOS+eyZHgt86g3x1xyVyN24OOedojYZljGLPAhFFydUfcDEmPJ9zlj4QRjNejGmLyQYVKVBsJoTxzxyR5Ze41j2+oy5Rgw8RsVviDUkjGOKC0B6Gq7o8zzcCh2gAjOqxSxw31dPm9n13AwtARCfrQoMwXuv1SyG+SQpH21bIrzEYUjrsxJZlDLr/P7WF+hObr9Q6ccrb718rcAyWLOIBYEvj8YQxDSBllUQnEM0CZk0JdTnSqXVZZQxiY5M4xfvp9mBdByesPyhd0WwqvD8gdTjRUroAaO+t1rmf6vX63p8PDEs3ZHcbUwwYqdbrd3HOFOra/nEHEFel2BrkhtxzydPo4jM7u9YGuEFvEMtTpQiUhtiKKJzTekpJxboUwZ3eCgnFK1HVRLHGNCNHXvV7yjEl1dCkosz+TcFnWCDEyWWyiW0TGvJMVB3r8QXdIKo7txWJmOzohFbOtW8xpw+g0qXumXcbdxLRGMA7MibV+TwyYvC6Mu0ZyBgK4xZzt3TKbUPxHzeMC0+UMS13OEGqUfWN5QXPxDHdLEZ9bBVw8tq4UKx5eLLIhfzfb2Tx0LFBOqZtVENwvUcGA07XG2YmO4V70+WP148sn1hhTWLQQotzdwUAttEgNrS0Oyd7a4Fhmtlkkq11qs7UutdZb6qVisx37xSZpmdWxsHWJQ0IJm7nFsUJqbZDMLSukxdaWepNkWd5ms9jtUqtNsja3NVktOGZtqWtaUm9taZRqcV1Lq0NqsjZbHajU0cqXqqqsFjtT1myx1S3ErrnW2mR1rDBJDVZHC9PZgErNUpvZ5rDWLWky26S2Jba2VrsFddSj2hZrS4MNrViaLegEKqprbVthszYudJhwkQMHTZLDZq63NJtti00MYSu6bJO4SDmiRB2SZSlbbF9obmqSaq0Ou8NmMTczWcZOY0trM+NoSUu92WFtbZFqLeiKubbJomBDV+qazNZmk1RvbjY3WuxxI0xMdSdOB1vQaGmx2MxNJsneZqmzsgbyaLVZ6hxcErlHJpo43LrWFrvl2iU4gHJRExiQhRZuAh0w4786joy734LuMj2OVpsjBmWZ1W4xSWab1c4gNNhaES6LJ65gPi5BPlnwWlS8LEZs7LPZgVJstepgvcXchArtDMZnZDG7LOtcciDMclvd3Ep55KVUqZ8mnrVKEcAUbvThxlXGeBPzGXcWP3mUChffXOxINqnll5UPzG48jZTy614rYxUMsVKC+8PPikmPJ8R3Oh6DXr967oWc3WgMV8WksF46u3FZKAZz7IaKHoiBoAeX9AQ9YSwmkjOCo0HPzepRHFSPqvEeMCvj8QflUABPKs9auXt9OcoG2XnGkXh8eN3yqq5z+lzh+dEaGpY6uXI3Oo6XsnJJf9H7WkWPZ42nwoM1al15oCtQoRZKUoc38QBZT4LEQzrxu0CYSGQGcZGZ+K4ilfiZja12lJBILcqE8bYeRmmZOPHbgglHrcSH8uXYMpNu/EjEFtMV4j0Z3zKuWYtPN0rqST22VqOGpSSCEi6UdaKWTi4pYZvpl1CLD58BlGlHvR6Uk3C9H+06+dx4PXauhWloRikf/tbir5+s+UK5L5pfyvGH0KqfY6pCL6rIlWPWRVddWGcHH1UYCavsMYbC6N98UoEftyq/FuXLUc6P7yD6LPO1Qc5OOeqQcU1DgrYoW9GofTZKbI5FQOaRlJFLP+lBWRaz/5lIsJjqL2hZYc6JrUTMn806PSn7Bz7M+j8jky/Mdtxnj8qixOedPMZezuoaHPNjZL8IC/Osjevzcm3xHFR0d/E5WfWrk1vx8Qxzcz0dfFaOWVMirGSTiePyc4Q+vj6g5rliwY9aw2qEPTwrFF9cKtNRnWGOYmyOO1HKxTMkoGqPamDSCnYlk2S+a5QMLk7IkmIeObbWzd8hjsuFa5yqf0oOujArvVxLmM9E+enAVreaxzNiGOMW2D5n+MO4F5Q8ZxbjnLCRAD79aCXCccbRuLkHYZ5r7Tgb5rNRG59vwaTuJRcii3AtCic9PAe6+J4Pq8x4+ViiR1H9wTFZqaCNcA5NCdFhbS+PZzTW8f0bwtWmz/HDFPOzgtcdiWtW9oOi26OyOjb6F/c6ypyCNhDL6PC4rIt71MP58H4pC9Hd0MFrpk/1UE6w6OZPZsPE34yJ1Sjh4voUmcQ87larZDRCLm7bzRF7VKTz+e50qKucqNHPK0M8Bom1KM7AZysBOy/C6m4IjZGN7pU4Y4k1IHGdxH12qpFqj9XtaK4pbCiV3HmRePr5GSOpsffyd7x+fJlYhNHzAD+3nKpH5WOYuthaxsn6GH4v330evpejFY1hD6tVTxlRkDJO3QkxT8y66PnFrCh8RVCLk6+LeuTmSFm8fAlsdKIc86ZLHQsm1FAnzx4ld6M2xvMT+kKfEmuce0yGOXmMLoTg4kjG2hvPy4UwmtS4d/N1notU9aBagWSOzztGb3QkFMvM6L4Zf4rIar2Tx0Sgh3vl5uuLL3AuFsf8Hr+CyUdP3eKEbFP2TtO4c6ad73t/AtaIuh+ikViLs54LMCaTdZxnn7qjA/hRTjEnr6xybEVi/BXMF98xXbzSS/wdUjHKPKM+P18U7y5Uw9lshEuNZfhCrEoJzCXG8O/dsyFePaNndnzXRXcUu0F0x+4gQXXFWI0BntFr8NmpRkw5F32c2/H3j/+NivX5XrWreySsnosdMaYWEgu300pasMfstGLPQZbhfdLG56w4JuF9zoYzS7FXj6P1PC5mPsPmi/luXIZtprGVLOG6FB02fDLdK3CE6ZZ4n/UWo3wL6mJrLWQ5t2FBbXYuaeO6m3G0Cd8WVY6tqMORJdhn7UbCbqOKvRZc5eB7h61jWBSkDhyPWx2LysotRpE1Y8+G+heqs2bUbeX6GH4TZ4q1W2I4G1SkZs4R08x01iGiJt5jo0vw3YZyds6nmfusoG3hPjTgvOKLhSNQIqEgqsN3G9pmEo2Iy8FRMEsOVdLEPWT+1PP1zOpiPqoga1WjzNpxLeUqlwoOxv/SmGU7978JPxL334EjDh4bM+qP6o3mTiPX0BzLoyXcPzPnoZVbqOVzjEXGZ1NM0pYQlTrOF4sbQ17PLZk5I/YLehLVNjY6F8qOqIVG7p+FM9XEpe3IowXlrbERJR+t3Nc6lVtFp5L3Sk40JbBbx31kkb0WrVrUnDJz7sZ6oewQhj/uhRIBs/qsS+AsHv0WNbp1sVi38iz7LCvL+F60cCkzj7U9xkID37/NKvIlCRkWjeMSNT9bY8jG8hvdR1G5L1M7FF1R22MjWM/zqUlFaI+x8cV6ldplwXPNxb/vhGN1e+zJnXh7jN9KE++fpoRam3gTUKpwI5f1jpOLjyr1WTmz4t95Eu9wFzq5ot+SlTt9/PYbvX0otVv5bpR4+3Xze7pyFwzFbiXK+eGP3Ux6+Gz8TFe+DXq5ROL3vRC3q3gWUVeM16XcL538tsCshS7A5sVOqPHfEAP8vFes9PB2WL2ZMP8iqiwbv3nct+LguG9VXxSDqC9fxH+QxzugfqfycIbZfbJc1Rsk0e9ncU4YA8pft7zjoh7PPqZtPhl/D2UcdCYgd6sRV/5Sxmzq/4G/r1VwvtfgbwXH6OY3v3J+Cw/g2NgbJSj/2eH8V8nK8f+Nlf0c0PQKOc/cfb1oniLkkAEC+Ozl/+ePYnsCf2aRLHwaedvAn5nkfnxm8Hb6M39oFM2lQjrZiL00UorPVFKFTz3Xl8KlkkkGPpN4W8dlRN7W8nHgIxo+ItSsoEApnNsIoxT+RuHTKvivIfjrRvjkbJ/4CYVPDmnPnlkhnu2Ds73aM6eniWdWwJka7elp8JePK8S/fAofV8CfKXxE4U9VcCob/jgAIwhxhMLI4Pk3as5r/9AIH550ix8OwEk3/J7CB7/LFz+g8Lt8+C2FE2vgNxR+PQTH358kHv8U3p8E7w3Aryj8ksIv3s0Rf0Hh3Rw4NgA//1mO+HMKP9uWKv4sB366Ed6ZD0exc3Q+vE3hrTf14lsU3tTDGxR+QuH1O4zi61PgP3PhNQqvDsArW0vFVyj8mMLLG+ElCj+i8EMKL96fLh6h8AKFwxR+QOEQ6juUDd9Pg+Hnh8RhCs8fvF58fgie79UeHCoVD14PB2u0Q6XwHIUDAzDYbxafpbAfX/s/hf9AXfsofM8Nz7jh3zNgbxZ8l8LTtOYcfIfCUxSezIInKDz+WIb4eBU8lgGP7jGKj86APUb49iNl4rc3wiNl8DCF3RQeorDrwUniLjc8+IBBfHASPGCAf9PDTgrfQiPfonB/Ouy4r1zcQeG+crgX7d87AAP3DIkDFO7B3LpnCO7p1d59Z6l49/Vwd412O4VvUrgL+3cNwZ2l0I9k9JvhG+jtN7JhWyr04UCfG7YiaVtL4Q4j3E7h6xRuo7Bls1HcQmGzEf6VwiYKXzPWil+zw60UetfBLV/dKN5C4asbYWMh/AuFDRlwM4UeCmspRMJpYiQTIoMCqTmmDadB+JA2lAWhGm2Qwk0UAhT8PrvoHwCfd4bos4N3BnRTWFMFqyl4qqDrU+gcgg4KMgU3BVd7oeii0E4MYnshOCmsonAjhRtWpoo3ZMD1bvjKS3Addq7LhpWpgBm9PBuWUVhKYUn+JHFJFTgo2CnYKFy7EdootGZDC4VmoUxsptA0BItnwCJrnrhoLljrskRrHiy05IkLKTRir9ENDdhrGAJLHtTjQP1cqKs1inVZUDeoqalJ0daaM8VaI9QOagj2zDUZojkTzIPCIezVVKeJNRlQMyj0Yq86LUWsToPqQaGmxq29hsLVCOHqT2EBhatmwHwK85DgeW64ctZk8crFMJfCFWXZ4hUU5iyGyysni5cvhtn4mk2hCgWrKMzC6VmToXIyVGCrIg/KU3LF8iEoM00Qy7KhbFDDzJoMRtE0AUwM7oD2sktLxcsoXIqSl5bCTM18cSaFGRSmU5iWCaW5tWKpBS7JhBIKxZmZYjGFIqlMLNoIUhlMXQyFaLmQQgGFKcjtFAr5GJX8STCZwiQKeRQmooaJDZCbUybm1kJOtkHMKYNsA0xAuQnZkIXrsygY0XNjLRjQgsEIBoW7zIw0MTMTMhXuMtL1YkYaZCjcpSN36XpIR+72adNSII3l1lxtKgU9eqKnkJILyQZIoqBD1ToKYjYAOgefggYHNPNBQABCGRADCIOCe/M24bL/Oz/knw3gH/wpIP8NehMDfQplbmRzdHJlYW0KZW5kb2JqCgo2IDAgb2JqCjY2NjkKZW5kb2JqCgo3IDAgb2JqCjw8L1R5cGUvRm9udERlc2NyaXB0b3IvRm9udE5hbWUvQkFBQUFBK0RlamFWdVNhbnNNb25vCi9GbGFncyA1Ci9Gb250QkJveFstNTU4IC0zNzQgNzE2IDEwNDFdL0l0YWxpY0FuZ2xlIDAKL0FzY2VudCA5MjgKL0Rlc2NlbnQgLTIzNQovQ2FwSGVpZ2h0IDEwNDEKL1N0ZW1WIDgwCi9Gb250RmlsZTIgNSAwIFI+PgplbmRvYmoKCjggMCBvYmoKPDwvTGVuZ3RoIDI2NC9GaWx0ZXIvRmxhdGVEZWNvZGU+PgpzdHJlYW0KeJxdkMtuxCAMRfd8BcvpYgTJvBopilTNNFIWfahpP4CAkyI1gAizyN8Xw7SVugAd2/eCbXbuLp3Rgb16K3sIdNRGeVjs1UugA0zakKKkSstwi9ItZ+EIi95+XQLMnRltXRP2FmtL8CvdPCg7wB1hL16B12aim49zH+P+6twXzGAC5aRpqIIxvvMk3LOYgSXXtlOxrMO6jZY/wfvqgJYpLnIr0ipYnJDghZmA1Jw3tG7bhoBR/2pVdgyj/BQ+Kouo5PxYNJHLxCeOvMv5R+R95hb5kDV75GPOX5BPmSvk+8wH5Crrd6mX26/YFa7tZ1oqr97HSdNu04g4nDbwu35nHbrS+QayzIAlCmVuZHN0cmVhbQplbmRvYmoKCjkgMCBvYmoKPDwvVHlwZS9Gb250L1N1YnR5cGUvVHJ1ZVR5cGUvQmFzZUZvbnQvQkFBQUFBK0RlamFWdVNhbnNNb25vCi9GaXJzdENoYXIgMAovTGFzdENoYXIgOQovV2lkdGhzWzYwMiA2MDIgNjAyIDYwMiA2MDIgNjAyIDYwMiA2MDIgNjAyIDYwMiBdCi9Gb250RGVzY3JpcHRvciA3IDAgUgovVG9Vbmljb2RlIDggMCBSCj4+CmVuZG9iagoKMTAgMCBvYmoKPDwvRjEgOSAwIFIKPj4KZW5kb2JqCgoxMSAwIG9iago8PC9Gb250IDEwIDAgUgovUHJvY1NldFsvUERGL1RleHRdCj4+CmVuZG9iagoKMSAwIG9iago8PC9UeXBlL1BhZ2UvUGFyZW50IDQgMCBSL1Jlc291cmNlcyAxMSAwIFIvTWVkaWFCb3hbMCAwIDYxMiA3OTJdL0dyb3VwPDwvUy9UcmFuc3BhcmVuY3kvQ1MvRGV2aWNlUkdCL0kgdHJ1ZT4+L0NvbnRlbnRzIDIgMCBSPj4KZW5kb2JqCgo0IDAgb2JqCjw8L1R5cGUvUGFnZXMKL1Jlc291cmNlcyAxMSAwIFIKL01lZGlhQm94WyAwIDAgNjEyIDc5MiBdCi9LaWRzWyAxIDAgUiBdCi9Db3VudCAxPj4KZW5kb2JqCgoxMiAwIG9iago8PC9UeXBlL0NhdGFsb2cvUGFnZXMgNCAwIFIKL09wZW5BY3Rpb25bMSAwIFIgL1hZWiBudWxsIG51bGwgMF0KL0xhbmcobmwtTkwpCj4+CmVuZG9iagoKMTMgMCBvYmoKPDwvQ3JlYXRvcjxGRUZGMDA1NzAwNzIwMDY5MDA3NDAwNjUwMDcyPgovUHJvZHVjZXI8RkVGRjAwNEYwMDcwMDA2NTAwNkUwMDRGMDA2NjAwNjYwMDY5MDA2MzAwNjUwMDJFMDA2RjAwNzIwMDY3MDAyMDAwMzMwMDJFMDAzMD4KL0NyZWF0aW9uRGF0ZShEOjIwMDkwOTI5MTcwMjQ0KzAyJzAwJyk+PgplbmRvYmoKCnhyZWYKMCAxNAowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDc4MTUgMDAwMDAgbiAKMDAwMDAwMDAxOSAwMDAwMCBuIAowMDAwMDAwMjEyIDAwMDAwIG4gCjAwMDAwMDc5NTggMDAwMDAgbiAKMDAwMDAwMDIzMiAwMDAwMCBuIAowMDAwMDA2OTg2IDAwMDAwIG4gCjAwMDAwMDcwMDcgMDAwMDAgbiAKMDAwMDAwNzIwMSAwMDAwMCBuIAowMDAwMDA3NTM0IDAwMDAwIG4gCjAwMDAwMDc3MjggMDAwMDAgbiAKMDAwMDAwNzc2MCAwMDAwMCBuIAowMDAwMDA4MDU3IDAwMDAwIG4gCjAwMDAwMDgxNTQgMDAwMDAgbiAKdHJhaWxlcgo8PC9TaXplIDE0L1Jvb3QgMTIgMCBSCi9JbmZvIDEzIDAgUgovSUQgWyA8OUQ1QThEMEM4MjUyOEE4QUU1MEExQTMxNjdBMDcwOTg+Cjw5RDVBOEQwQzgyNTI4QThBRTUwQTFBMzE2N0EwNzA5OD4gXQovRG9jQ2hlY2tzdW0gL0UzQzg2NDU4NUFGRDQzMTM5NzA4QTlCRkQ4RUQxOEVFCj4+CnN0YXJ0eHJlZgo4MzQxCiUlRU9GCg=="
			+ "				</ns2:base64attachment>"
			+ "			</ns2:attachment>"
			+ "		</ns2:terugmelding>" + "	</S:Body>" + "</S:Envelope>";

	@Override
	protected String getConfigResources() {
		return "guc_generic_config.xml,rtmfguc-config.xml,rtmfguc-mocks-config.xml,rtmfguc-zm-config.xml,rtmfguc-mocks-zm-config.xml";
	}

	@Override
	public void doSetUp() throws Exception {
		super.doSetUp();
		// start smtp server mock
		smtpServer = new Wiser();
		smtpServer.setPort(18089);
		smtpServer.start();
		setDisposeManagerPerSuite(true);
	}

	@Override
	public void doTearDown() throws Exception {
		if (null != smtpServer) {
			smtpServer.stop();
		}
		super.doTearDown();
	}

	public void testToFile() throws Exception {
		MuleClient client = new MuleClient();
		StelselCatalogusCache cache = (StelselCatalogusCache) client
		.getProperty("stelselCatalogusCacheBean");
		
		cache.addKey("GM-PERSOON", "GM", false, "Persoon");
		MuleMessage response = client.send(
				"vm://guc/rtmfguc/terugmeldServiceIn", new ByteArrayInputStream(zendTerugmeldingGM.getBytes()),
				null);
		assertNotNull("De response is null.", response);
		assertNotNull("De payload is null.", response.getPayload());
		String responsePayload = response.getPayloadAsString();
		assertNotSame("Er is een {NullPayload} teruggegeven.", "{NullPayload}",
				responsePayload);
//		assertEquals(
//				"De verwachte waarde van bereikenAdres komt niet overeen.",
//				"//tmp/GM2/", response.getProperty("bronhouder.bereikenAdres",
//						true));
	}

	public void testToEmail() throws Exception {

		MuleClient client = new MuleClient();
		
		StelselCatalogusCache cache = (StelselCatalogusCache) client
		.getProperty("stelselCatalogusCacheBean");
		
		cache.addKey("GM-PERSOON", "GM", false, "Persoon");
		
		MuleMessage response = client.send("vm://guc/rtmfguc/terugmeldServiceIn",
				new DefaultMuleMessage(new ByteArrayInputStream(emailTerugmeldingZM.getBytes())));
		assertNotNull("De response is null.", response);
		assertNotNull("De payload is null.", response.getPayload());
		String responsePayload = response.getPayloadAsString();
		assertNotSame("Er is een {NullPayload} teruggegeven.", "{NullPayload}",
				responsePayload);
//		assertEquals(
//				"De verwachte waarde van bereikenAdres komt niet overeen.",
//				"guc.rtmf@gmail.com", response.getProperty("toAddresses",
//						"<onbekend>"));
		assertTrue("Er zijn geen emails gevonden op de SMTP server.",
				smtpServer.getMessages().size() > 0);

		List<WiserMessage> emails = smtpServer.getMessages();
		for (WiserMessage message : emails) {
			message.dumpMessage(System.out);
			MimeMessage mm = message.getMimeMessage();
			Multipart mp = (Multipart) mm.getContent();

			int nrOfAt = 0;
			for (int i = 0, n = mp.getCount(); i < n; i++) {
				Part part = mp.getBodyPart(i);
				// workaround voor bug in mule 2.2.1, zie ticket #130 en MULE-4303 
				if(part.isMimeType("application/pdf") || part.isMimeType("text/xml")) {
				//if ((disposition != null)
				//		&& ((disposition.equals(Part.ATTACHMENT) || (disposition
				//				.equals(Part.INLINE))))) {
					nrOfAt++;
					System.out.println("De naam van de attachment is: "
							+ part.getFileName());
				}
			}
			assertEquals("Er is een verkeerd aantal attachments gevonden.", 3,
					nrOfAt);
		}
	}
}
