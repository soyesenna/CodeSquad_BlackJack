# CodeSquad_BlackJack

## 1. V1 전략
### V1 흐름
1. 무한하게 많은 덱을 만든다
2. 덱을 랜덤하게 섞는다
3. 플레이어와 딜러가 카드를 뽑는다
4. 카드 숫자에 따라 승패를 결정한다
5. 한게임 더?
   1. yes -> 3번
   2. no -> 종료


--- 
## 사용한 Enum
## InputStatus
- GOOD, BAD

## PlayerName
- DEALER, USER

## 사용할 인터페이스
  
## Deck
- pollLast()
  - parameter : none
  - return : Card
  - 덱의 맨 마지막에서 카드를 꺼낸다
- initialize()
  - parameter : none
  - return : void
  - 덱을 초기화한다

## Player
- getAccumCards()
  - parameter : none
  - return : List\<Card>
  - 현재 플레이어가 가진 카드들을 리턴

## GameManager
- printGameStatus()
  - parameter : none
  - return : void
  - 현재 게임의 상태를 출력해준다
- input()
  - parameter : none
  - return : String
  - 유저의 입력을 받는 메서드
- checkInput(String)
  - parameter : String
  - return : InputStatus
  - 입력이 올바른지 검사한다
- doGame()
  - parameter : none
  - return : void
  - 게임 진행을 총괄한다
- checkGameWinenr(Player, Player)
  - parameter : Player, Player
  - return : PlayerName
  - 두 플레이어를 받아서 마지막 카드를 보고 게임의 승자를 결정한다

## 사용할 클래스

## Card
### variables
- num
  - type : int
  - accss modifier : private
  - 카드의 숫자 상태 저장

### methods
- Card(Card)
  - 생성자
  - Card를 받아서 받은 Card의 num으로 자신의 num을 초기화한다
- Card(int)
  - 생성자
  - int를 받아서 자신의 num을 초기화한다
- getNum()
  - parameter : none
  - return : int
  - num을 반환해준다

## DeckV1
- implements Deck
### variables
- deck
  - type : Deque\<Card>
  - access modifier : private
  - 카드 덱을 저장

### methods
- initialize()
  - Override
  - parameter : none
  - return : void
  - access modifier : public
  - deck 변수를 초기화하는 메서드
- pollLast()
  - Override
  - parameter : none
  - return : Card
  - access modifier : public
  - 덱의 맨 마지막 카드를 뽑아 리턴해주는 메서드
  - 무한한 덱이므로 뽑은 카드를 다시 덱의 맨 앞에 삽입해준다

## PlayerV1
- implements Player
### variables
- cards
  - type : List\<Card>
  - access modifier : private
  - 플레이어가 받은 카드들을 저장

### methods
- getAccumCards()
  - Override
  - parameter : none
  - return : List\<Card>
  - cards 변수를 복사해서 리턴한다 
  
## GameManagerV1
- implements GameManager
### variables
- br
  - type : final BufferedReader
  - access modifier : private
  - 입력을 받기위한 스트림변수
- players
  - type : final Map\<PlayerName, Player>
  - access modifier : private
  - 플레이어 객체들을 저장하는 변수

### methods
