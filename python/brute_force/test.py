import unittest
import time

from brute_force.main import *


class BruteForseTest(unittest.TestCase):
    def test_return_types(self):
        self.addTypeEqualityFunc(
            generate_key("pass", "user", "tect", "9940000000", "sender"), str
        )

        self.addTypeEqualityFunc(generate_signature("lorem"), str)

    
    def test_brute_force_length_3(self):
        start = time.time()
        password = "aaa"
        char_count = 3

        lsim = Lsim(
            username="lsim",
            secret=password,
            sender="example",
            phone="994000000000",
            text="lorem ipsum dolor",
        )

        lsim.generate_link()
        params = lsim.read_params()

        result = brute_force(params=params, char_count=char_count)

        print("Password is:", result)

        print("Process finished for 3 length:", str(time.time() - start))

        self.assertEqual(password, result)

    
    def test_brute_force_length_4(self):
        start = time.time()
        password = "asd4"
        char_count = 4

        lsim = Lsim(
            username="lsim",
            secret=password,
            sender="example",
            phone="994000000000",
            text="lorem ipsum dolor",
        )

        lsim.generate_link()
        params = lsim.read_params()

        result = brute_force(params=params, char_count=char_count)

        print("Password is:", result)

        print("Process finished for 4 length:", str(time.time() - start))

        self.assertEqual(password, result)
    
    def test_brute_force_length_5(self):
        start = time.time()
        password = "aaaaa"
        char_count = 5

        lsim = Lsim(
            username="lsim",
            secret=password,
            sender="example",
            phone="994000000000",
            text="lorem ipsum dolor",
        )

        lsim.generate_link()
        params = lsim.read_params()

        result = brute_force(params=params, char_count=char_count)

        print("Password is:", result)

        print("Process finished for 5 length:", str(time.time() - start))

        self.assertEqual(password, result)

