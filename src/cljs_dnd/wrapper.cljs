
(ns cljs-dnd.wrapper)

;; TODO
(defn drag-wrapper)

;; TODO
(defn drop-wrapper)

(defn wrapper []
  (.createClass
    reagent/react
    #js {:componentWillMount (fn [])
         :componentDidMount (fn [])
         :componentWillUpdate (fn [next-props]
                                (this-as this
                                  (when (and next-props
                                             (.-props this)))))
         :render (fn []
                   (this-as this
                     (let [app-db (.-wrap (.-props this))
                           children (.-children (.-props this))]
                       (reagent/as-element
                            [:div.board-content
                              children]))))}))

; (defn draggable []
;   (reagent/create-class {:reagent-render draggable-render
;                          :component-did-mount         draggable-did-mount}))

; (defn greeter
;   [name]
;   [:div "Hello: " name])
;
; (defn decorate
;   [& HoCs]
;   (into [:div] HoCs))
;
; ;; markup:
; [decorate [greeter "Spot"] [greeter "Fiddo"]]
