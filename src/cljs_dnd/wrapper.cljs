
(ns cljs-dnd.wrapper
  (:require [reagent.core :as r]
            [cljs-dnd.state :refer [add-source!
                                    add-target!]]))

(defn drag-wrapper [opts component]
  (let [{:keys [drag-type]} opts]
    (prn opts)
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
           ;; dispatch the registration of this dom el as draggable
           (let [node (reagent.dom/dom-node this)]
             (do
               (add-source! node)
               (aset node "draggable" true))))
       :component-will-update
         (fn [this new-argv])
       :component-did-update
         (fn [this old-argv])
       :component-will-unmount
         (fn [this])
       :render
          (fn []
             (this-as this
              (r/create-element
                "div"
                (clj->js opts)
                (r/as-element component))))})))
