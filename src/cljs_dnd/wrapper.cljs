
(ns cljs-dnd.wrapper
  (:require [reagent.core :as r]))

(defn drag-wrapper [opts component]
  (let [{:keys [drag-type]} opts]
    (r/create-class
      {:get-initial-state
         (fn [this])
       :component-will-receive-props
         (fn [this new-argv])
       :should-component-update
         (fn [this old-argv new-argv])
       :component-will-mount
         (fn [this])
       :component-did-mount
         (fn [this]
           (let [node (reagent.dom/dom-node this)]
             (aset node "draggable" true)))
       :component-will-update
         (fn [this new-argv])
       :component-did-update
         (fn [this old-argv])
       :component-will-unmount
         (fn [this])
       :render
          (fn []
             (this-as this
               (r/as-element component)))})))
