package com.example.labpokemons.services;

import static org.mockito.Mockito.mockStatic;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class OkHttpRequestDiffblueTest {
    /**
     * Method under test: {@link OkHttpRequest#get(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGet() throws IOException {
        try (MockedStatic<InetAddress> mockInetAddress = mockStatic(InetAddress.class)) {
            mockInetAddress.when(() -> InetAddress.getAllByName(Mockito.<String>any())).thenReturn(new InetAddress[]{});
            OkHttpRequest.get("https://config.us-east-2.amazonaws.com");
        }
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.net.UnknownHostException: okhttp3.Dns$Companion$DnsSystem@6067408f returned no addresses for pokeapi.co
        //       at okhttp3.internal.connection.RouteSelector.resetNextInetSocketAddress(RouteSelector.kt:166)
        //       at okhttp3.internal.connection.RouteSelector.nextProxy(RouteSelector.kt:129)
        //       at okhttp3.internal.connection.RouteSelector.next(RouteSelector.kt:71)
        //       at okhttp3.internal.connection.ExchangeFinder.findConnection(ExchangeFinder.kt:205)
        //       at okhttp3.internal.connection.ExchangeFinder.findHealthyConnection(ExchangeFinder.kt:106)
        //       at okhttp3.internal.connection.ExchangeFinder.find(ExchangeFinder.kt:74)
        //       at okhttp3.internal.connection.RealCall.initExchange$okhttp(RealCall.kt:255)
        //       at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.kt:32)
        //       at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
        //       at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.kt:95)
        //       at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
        //       at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.kt:83)
        //       at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
        //       at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.kt:76)
        //       at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.kt:109)
        //       at okhttp3.internal.connection.RealCall.getResponseWithInterceptorChain$okhttp(RealCall.kt:201)
        //       at okhttp3.internal.connection.RealCall.execute(RealCall.kt:154)
        //       at com.example.labpokemons.services.OkHttpRequest.get(OkHttpRequest.java:27)
        //   See https://diff.blue/R013 to resolve this issue.
    }
}
