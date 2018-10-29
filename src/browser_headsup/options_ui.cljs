(ns browser-headsup.options-ui
  (:require
   [browser-headsup.localstorage :as localstorage]))

(defn on-factory-reset
  []
  (localstorage/clear!)
  (-> js/window .-location .reload))

(defn main-section
  []
  [:div
   [:button
    {:on-click #(on-factory-reset)}
    "Factory Reset"]])

(defn options-ui
  []
  [main-section])
