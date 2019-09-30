(ns browser-headsup.api
  (:require
   [browser-headsup.core :as headsup-core]
   [browser-headsup.ui :as headsup-ui]
   [browser-headsup.options-ui :as options-ui]))

;; Color palette and styling values for content

(def col-bg headsup-ui/col-bg)
(def col-default headsup-ui/col-default)
(def col-default2 headsup-ui/col-default2)
(def col-error headsup-ui/col-error)
(def col-highlight headsup-ui/col-highlight)
(def col-highlight2 headsup-ui/col-highlight2)
(def col-dimmed headsup-ui/col-dimmed)
(def spacing headsup-ui/spacing)
(def font-size headsup-ui/font-size)


;; Provide a mechanism to disable the headsup.
;; In production this should yield false.
(def enabled? goog.DEBUG)


(defn def-tab
  [id title component]
  (headsup-core/def-tab {:id id
                         :title title
                         :component component}))


(defn with-db
  [f]
  (headsup-core/with-db f))


(defn- install!
  []
  (.log js/console "Installing browser-headsup")
  (headsup-ui/install!))


;; The headsup must be installed in the browser exactly once.

(defonce installed
  (do
    (when enabled?
      (def-tab :headsup-options "Options" [options-ui/options-ui])
      (install!))
    true))
