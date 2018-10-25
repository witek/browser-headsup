(ns browser-headsup.api
  (:require
   [browser-headsup.core :as headsup-core]
   [browser-headsup.ui :as headsup-ui]))


;; Provide a mechanism to disable the headsup.
;; In production this should yield false.
;; TODO check if this works for production builds

(def enabled?
  (-> js/window .-goog .-DEBUG))


(defn- install!
  []
  (.log js/console "Installing browser-headsup")
  (headsup-ui/install!))


;; The headsup must be installed in the browser exactly once.

(defonce installed
  (do
    (when enabled?
      (install!))
    true))
