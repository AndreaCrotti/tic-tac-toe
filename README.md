# tic-tac-toe

[![Build Status](https://travis-ci.org/AndreaCrotti/tic-tac-toe.svg?branch=master)](https://travis-ci.org/AndreaCrotti/tic-tac-toe)

## Usage

    lein run

## TODO

- store game results with an in memory datascript database
- generate some interesting reports about the winner
- implement more strategies
    - [x] random algorithm
    - [ ] blocker algorithm, simply try to block the opponent
    - [ ] best move algorithm

- make the various algorithm compete against each other and see who the winner is
  (could use core.async for that as well)
- implement a UI with re-frame to show the whole thing up and running
- add CLI interface reading from stdin

## License

Copyright © 2017 Andrea Crotti

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
