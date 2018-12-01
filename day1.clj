(let [input (map #(Integer/parseInt %)
                 (clojure.string/split
                  (slurp "day1input.txt") #"\n"))
      part1_answer (reduce + 0 input)
      part2_answer (atom 0)
      freqs (atom {})
      found (atom false)]

  (println part1_answer)

  (while (not @found)
    (doseq [item input]
      (swap! part2_answer + item)
      (let [current (@freqs @part2_answer)]
        (if (and (not (nil? current)) (false? @found))
          (do (reset! found true) (println @part2_answer))
          (swap! freqs assoc @part2_answer 1))))))
