
    (function() {
      var baseURL = "https://cdn.shopify.com/shopifycloud/checkout-web/assets/";
      var scripts = ["https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/polyfills.DtCK9L21.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/app.BntuqhmM.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/VaultedContact.37NIixLZ.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/DeliveryMethodSelectorSection.DJ_zZnb2.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/useUnauthenticatedErrorModal.BP3LexRQ.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/LegacyVaultedShippingMethods.BUdTRFW3.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/Rollup.Bp2Di0lI.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/RageClickCapture.CyDOq3ZA.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/ShopPayLogo.Cxdn-QOp.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/PurchaseOptionsAgreement.CdoQ4QJi.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/PickupPointCarrierLogo.dyx_kYGh.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/hooks.CR5BECt2.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/VaultedPayment.CI7U-XR3.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/Option.9Lwm8NJY.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/OnePageModal.DcR61Tka.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/useShowShopPayOptin.B4y6ghs0.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/Section.DMTBfVrR.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/useGooglePaySdk.Cm57pAfY.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/PayButtonSection.DXM7BFTe.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/context.CblJUAIT.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/publishMessage.DkgkjqCz.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/DutyOptions.Nd090x9r.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/SubscriptionPriceBreakdown.Dg12g6fE.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/useAmazonContact.DMZP2arh.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/StockProblemsLineItemList.B4kD5Twz.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/component-ShopPayVerificationSwitch.BKUhHy-A.js","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/index.hh2stL0x.js"];
      var styles = ["https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/app.C4Prma-H.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/VaultedContact.BL1uXB7_.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/DeliveryMethodSelectorSection.Got04t9H.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/Rollup.o9Mx-fKL.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/RageClickCapture.DnkQ4tsk.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/ShopPayLogo.D_HPU8Dh.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/PickupPointCarrierLogo.C0wRU6wV.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/VaultedPayment.-UsM8FFz.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/Option.BgrbqXV7.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/Section.sQehCocD.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/PayButtonSection.DF7trkKf.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/DutyOptions.Bd1Z60K2.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/SubscriptionPriceBreakdown.Bqs0s4oM.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/useAmazonContact.D-Ox6Dnf.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/StockProblemsLineItemList.CxdIQKjw.css","https://cdn.shopify.com/shopifycloud/checkout-web/assets/c1.en/assets/ShopPayVerificationSwitch.CAxiAssW.css"];
      var fontPreconnectUrls = [];
      var fontPrefetchUrls = [];
      var imgPrefetchUrls = [];

      function preconnect(url, callback) {
        var link = document.createElement('link');
        link.rel = 'dns-prefetch preconnect';
        link.href = url;
        link.crossOrigin = '';
        link.onload = link.onerror = callback;
        document.head.appendChild(link);
      }

      function preconnectAssets() {
        var resources = [baseURL].concat(fontPreconnectUrls);
        var index = 0;
        (function next() {
          var res = resources[index++];
          if (res) preconnect(res, next);
        })();
      }

      function prefetch(url, as, callback) {
        var link = document.createElement('link');
        if (link.relList.supports('prefetch')) {
          link.rel = 'prefetch';
          link.fetchPriority = 'low';
          link.as = as;
          if (as === 'font') link.type = 'font/woff2';
          link.href = url;
          link.crossOrigin = '';
          link.onload = link.onerror = callback;
          document.head.appendChild(link);
        } else {
          var xhr = new XMLHttpRequest();
          xhr.open('GET', url, true);
          xhr.onloadend = callback;
          xhr.send();
        }
      }

      function prefetchAssets() {
        var resources = [].concat(
          scripts.map(function(url) { return [url, 'script']; }),
          styles.map(function(url) { return [url, 'style']; }),
          fontPrefetchUrls.map(function(url) { return [url, 'font']; }),
          imgPrefetchUrls.map(function(url) { return [url, 'image']; })
        );
        var index = 0;
        (function next() {
          var res = resources[index++];
          if (res) prefetch(res[0], res[1], next);
        })();
      }

      function onLoaded() {
        preconnectAssets();
        prefetchAssets();
      }

      if (document.readyState === 'complete') {
        onLoaded();
      } else {
        addEventListener('load', onLoaded);
      }
    })();
  