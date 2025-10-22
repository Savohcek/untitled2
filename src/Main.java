//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
       Scanner in = new Scanner(System.in);
    System.out.print("Введите сумму:");
       double a = in.nextDouble();
    System.out.println("Выберите валюту\n1-Доллар USD\n2-Евро EUR\n3-Тенге KZT\n4-Йена JPY");
    int x = in.nextInt();
    double z = 0;
    if ( x==1) {
        z = a / 81.35;
        System.out.printf("Получается Доллара USD: ");
        System.out.printf("%.2f",z);

    }
    if ( x==2) {
         z = a/94.67;
        System.out.printf("Получается Евро EUR :");
        System.out.printf("%.2f",z);

    }
    if ( x==3) {
         z = a/0.1512;
        System.out.printf("Получается Тенге KZT :");
        System.out.printf("%.2f",z);

    }
    if ( x==4) {
         z = a/0.5387;
        System.out.printf("Получается Йена JPY :");
        System.out.printf("%.2f",z);

    }



}
