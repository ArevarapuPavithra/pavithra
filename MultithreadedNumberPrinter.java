class NumberPrinter
{
    private int currentNumber = 2;
    private boolean primeTurn = true;
    private boolean isPrime(int num)
    {
        if(num < 2) return false;
        for(int i = 2; i * i <= num; i++)
        {
            if(num % i == 0) return false;
        }
        return true;
    }
    public synchronized void printPrime()
    {
        while(currentNumber <= 100)
        {
            while(!primeTurn){
                try{
                    wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(currentNumber > 100) break;
            if(isPrime(currentNumber)){
                System.out.println("Prime: " + currentNumber);
                primeTurn = false;
                currentNumber++;
            }else{
                currentNumber++;
                continue;
            }
            notify();
        }
    }
    public synchronized void printEven()
    {
        while(currentNumber <= 100){
            while(primeTurn){
                try{
                    wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            if(currentNumber > 100) break;
            if(currentNumber % 2 == 0){
                System.out.println("Even: " + currentNumber);
                primeTurn = true;
                currentNumber++;
            }else{
                currentNumber++;
                continue;
            }
            notify();
        }
    }
}
public class MultithreadedNumberPrinter
{
    public static void main(String[] args)
    {
        NumberPrinter printer = new NumberPrinter();
        Thread primeThread = new Thread(printer::printPrime);
        Thread evenThread = new Thread(printer::printEven);
        primeThread.start();
        evenThread.start();
    }
}